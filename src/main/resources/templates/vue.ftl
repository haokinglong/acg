<template>
  <div class="${name?lower_case}">
    <div class="app-content grid-panel">
      <div class="toolbar">
        <s-form ref="queryForm" inline :data.sync="query" :components="queryComponents">
          <el-button type="primary" @click="load(1)">查 询</el-button>
          <el-button @click="PClearQueryPage">重 置</el-button>
          <el-button type="primary" @click="add">新 增</el-button>
        </s-form>
      </div>
      <el-table :data="rows" style="width: 100%" @row-click="PToDetail">
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <#list metaColumns as column>
          <#if column.camelName?uncap_first !="id" && column.camelName?uncap_first !="createTime" && column.camelName?uncap_first !="createBy" && column.camelName?uncap_first !="updateTime" && column.camelName?uncap_first !="updateBy">
            <el-table-column align="center" prop="${column.camelName?uncap_first}" label="${column.cmt}"></el-table-column>
          </#if>
        </#list>
        <el-table-column width="150px" align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click.stop="toUpdate(scope.row)">编辑</el-button>
            <el-button type="text" @click.stop="toDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="load" v-show="query.total > 0" :page-size="query.limit" :pager-count="11" layout="total, prev, pager, next" :total="query.total" :background="true" :current-page.sync="query.page" class="pagination"></el-pagination>
      <side-dialog @close="closeAddModal" class="side-dialog" :modal.sync="addModal" :title="detailFlag?'查看':(editId?'编辑':'新增')">
        <div class="body">
          <div :class="detailFlag?'qx-detail-form':'qx-standard-forms'">
            <div class="title">基本信息<i class="el-icon-arrow-down" @click="PHideDetailItem"></i></div>
            <s-form ref="form" :isDetail="!!detailFlag" labelPosition="left" labelWidth="150px" :data.sync="form" :rules="formRule" :components="components"></s-form>
          </div>
          <div :class="detailFlag?'qx-detail-form':'qx-standard-forms'">
            <div class="title">上报信息<i class="el-icon-arrow-down" @click="PHideDetailItem"></i></div>
            <report-info :isDetail="!!detailFlag" :info="reportInfo"></report-info>
          </div>
        </div>
        <div slot="footer"  v-if="!detailFlag" >
          <el-button type="text" @click="actionAction">选 择</el-button>
        </div>
      </side-dialog>
    </div>
  </div>
</template>

<script>
  import { ElPage } from '@/el-form/index'
    export default {
        mixins: [ElPage],
        name: '${name?lower_case}',
        data() {
            return {
                query: {
                    page: 1,
                    limit: 10,
                    total: 0,
                    areaCode: [],
                  <#list metaColumns as column>
                  <#if column.operation = "L" || column.operation = "R" || column.operation = "A">
                  '${column.camelName?uncap_first}': '',
                  </#if>
                  </#list>
                },
                queryComponents: [
                { name: 'SCascader', label: '所属区域', key: 'areaCode', styles: { width: '180px' } }
                <#list metaColumns as column>
                  <#if column.operation = "L" || column.operation = "R" || column.operation = "A">
                    { name: 'SInput', label: '${column.cmt}', key: '${column.camelName?uncap_first}', styles: { width: '180px' } },
                </#if>
                </#list>
                ],
                rows: [],
                addModal: true,
                editId: '',
                form: {
            <#list metaColumns as column>
            <#if column.camelName?uncap_first != "id" && column.camelName?uncap_first != "createTime" && column.camelName?uncap_first != "createBy"&& column.camelName?uncap_first != "updateTime" && column.camelName?uncap_first != "updateBy">
            ${column.camelName?uncap_first}: '',
            </#if>
            </#list>
        },
            formRule: {
                <#list metaColumns as column>
                <#if column.camelName?uncap_first != "id" && column.camelName?uncap_first != "createTime" && column.camelName?uncap_first != "createBy"&& column.camelName?uncap_first != "updateTime" && column.camelName?uncap_first != "updateBy">
                ${column.camelName?uncap_first} : {required: true},
                </#if>
                </#list>
            },
            components: [
                <#list metaColumns as column>
                <#if column.camelName?uncap_first != "id" && column.camelName?uncap_first != "createTime" && column.camelName?uncap_first != "createBy"&& column.camelName?uncap_first != "updateTime" && column.camelName?uncap_first != "updateBy">
                {name: 'SInput', label: '${column.cmt}', key: '${column.camelName?uncap_first}'},
                </#if>
                </#list>
            ],
                reportInfo: {}
        }
        },
        created() {
            this.load(1)
        },
        methods: {
            load(page) {
                this.query.page = page || 1
                let data = this.PPFormToData(this.query)
                let params = this.$biz.PDealQueryParams(data, [])
                this.$http.request({
                    method: 'get',
                    url: `/api/${camelName?uncap_first}s/page`,
                    params
                }).then((res) => {
                    if (!res) return
                    let data = res.data.data
                    this.rows = data.data
                    this.query.total = data.total
                })
            },
            add() {
                this.addModal = true
                this.reportInfo = {}
            },
            async toUpdate(row) {
                let res = await this.queryDetail(row)
                if (!res) return
                this.editId = row.id
                this.reportInfo = { ...res.data.data }
                this.form = this.PDataToForm(res.data.data, this.form)
                this.addModal = true
            },
            toDelete(row) {
                this.$confirm('您确认要删除该中心吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.request({
                        method: 'delete',
                        url: `/api/${camelName?uncap_first}s/`+ this.editId
                    }).then((res) => {
                        if (res.data.code !== 200) return
                        this.$message.success('删除成功')
                        this.load(1)
                    })
                })
            },
            queryDetail(row) {
                return this.$http.request({
                    method: 'get',
                    url: `/api/${camelName?uncap_first}s/`+ row.id
                }).then((res) => {
                    return res
                })
            },
            closeAddModal() {
                this.addModal = false
                this.$refs.form.resetFields()
            },
            actionAction() {
                this.$refs.form.validate((valid) => {
                    if (!valid) return
                    let data = this.PFormToData(this.form)
                    this.$http.request({
                        method: this.editId ? 'put' : 'post',
                        url: this.editId ? `/api/${camelName?uncap_first}s/`+ this.editId : `/api/${camelName?uncap_first}s`,
                        data
                    }).then((res) => {
                        if (!res) return
                        this.$message.success(this.editId ? '修改成功' : '添加成功')
                        this.load(1)
                        this.closeAddModal()
                    })
                })
            }
        }
    }
</script>
