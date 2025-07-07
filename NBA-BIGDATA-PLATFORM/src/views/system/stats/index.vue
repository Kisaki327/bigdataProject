<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="赛季，例如 2023-24" prop="Season">
        <el-input
          v-model="queryParams.Season"
          placeholder="请输入赛季，例如 2023-24"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队唯一ID" prop="Teamid">
        <el-input
          v-model="queryParams.Teamid"
          placeholder="请输入球队唯一ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队深色Logo图片URL" prop="LogoDark">
        <el-input
          v-model="queryParams.LogoDark"
          placeholder="请输入球队深色Logo图片URL"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队浅色Logo图片URL" prop="LogoLight">
        <el-input
          v-model="queryParams.LogoLight"
          placeholder="请输入球队浅色Logo图片URL"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队名称" prop="teamname">
        <el-input
          v-model="queryParams.teamname"
          placeholder="请输入球队名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出场总场数" prop="Games">
        <el-input
          v-model="queryParams.Games"
          placeholder="请输入出场总场数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="首发场次数" prop="Gamesstarted">
        <el-input
          v-model="queryParams.Gamesstarted"
          placeholder="请输入首发场次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总上场分钟数" prop="Mins">
        <el-input
          v-model="queryParams.Mins"
          placeholder="请输入总上场分钟数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场均得分" prop="Pointspg">
        <el-input
          v-model="queryParams.Pointspg"
          placeholder="请输入场均得分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场均篮板" prop="Rebspg">
        <el-input
          v-model="queryParams.Rebspg"
          placeholder="请输入场均篮板"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场均助攻" prop="Assistspg">
        <el-input
          v-model="queryParams.Assistspg"
          placeholder="请输入场均助攻"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场均抢断" prop="Stealspg">
        <el-input
          v-model="queryParams.Stealspg"
          placeholder="请输入场均抢断"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场均盖帽" prop="Blockspg">
        <el-input
          v-model="queryParams.Blockspg"
          placeholder="请输入场均盖帽"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投篮命中率" prop="FgpctStr">
        <el-input
          v-model="queryParams.FgpctStr"
          placeholder="请输入投篮命中率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三分命中率" prop="TppctStr">
        <el-input
          v-model="queryParams.TppctStr"
          placeholder="请输入三分命中率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="罚球命中率" prop="FtpctStr">
        <el-input
          v-model="queryParams.FtpctStr"
          placeholder="请输入罚球命中率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进攻篮板总数" prop="Offrebs">
        <el-input
          v-model="queryParams.Offrebs"
          placeholder="请输入进攻篮板总数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="防守篮板总数" prop="Defrebs">
        <el-input
          v-model="queryParams.Defrebs"
          placeholder="请输入防守篮板总数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="失误次数" prop="Turnovers">
        <el-input
          v-model="queryParams.Turnovers"
          placeholder="请输入失误次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="犯规次数" prop="Fouls">
        <el-input
          v-model="queryParams.Fouls"
          placeholder="请输入犯规次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:stats:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:stats:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:stats:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:stats:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="statsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="赛季" align="center" prop="season" />
      <el-table-column label="球队ID" align="center" prop="teamid" />
      <el-table-column label="球队深色Logo" align="center" prop="logoDark" >
        <template slot-scope="scope">
          <image-preview :src="scope.row.logoDark" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="球队浅色Logo" align="center" prop="logoLight">
        <template slot-scope="scope">
          <image-preview :src="scope.row.logoLight" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="球队名称" align="center" prop="teamname" />
      <el-table-column label="出场总场数" align="center" prop="games" />
      <el-table-column label="首发场次数" align="center" prop="gamesstarted" />
      <el-table-column label="总上场分钟数" align="center" prop="mins" />
      <el-table-column label="场均得分" align="center" prop="pointspg" />
      <el-table-column label="场均篮板" align="center" prop="rebspg" />
      <el-table-column label="场均助攻" align="center" prop="assistspg" />
      <el-table-column label="场均抢断" align="center" prop="stealspg" />
      <el-table-column label="场均盖帽" align="center" prop="blockspg" />
      <el-table-column label="投篮命中率" align="center" prop="fgpctStr" />
      <el-table-column label="三分命中率" align="center" prop="tppctStr" />
      <el-table-column label="罚球命中率" align="center" prop="ftpctStr" />
      <el-table-column label="进攻篮板总数" align="center" prop="offrebs" />
      <el-table-column label="防守篮板总数" align="center" prop="defrebs" />
      <el-table-column label="失误次数" align="center" prop="turnovers" />
      <el-table-column label="犯规次数" align="center" prop="fouls" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:stats:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:stats:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改球队统计信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="赛季，例如 2023-24" prop="Season">
          <el-input v-model="form.Season" placeholder="请输入赛季" />
        </el-form-item>
        <el-form-item label="球队唯一ID" prop="Teamid">
          <el-input v-model="form.Teamid" placeholder="请输入球队唯一ID" />
        </el-form-item>
        <el-form-item label="球队深色Logo图片URL" prop="LogoDark">
          <el-input v-model="form.LogoDark" placeholder="请输入球队深色Logo图片URL" />
        </el-form-item>
        <el-form-item label="球队浅色Logo图片URL" prop="LogoLight">
          <el-input v-model="form.LogoLight" placeholder="请输入球队浅色Logo图片URL" />
        </el-form-item>
        <el-form-item label="球队名称" prop="teamname">
          <el-input v-model="form.teamname" placeholder="请输入球队名称" />
        </el-form-item>
        <el-form-item label="出场总场数" prop="Games">
          <el-input v-model="form.Games" placeholder="请输入出场总场数" />
        </el-form-item>
        <el-form-item label="首发场次数" prop="Gamesstarted">
          <el-input v-model="form.Gamesstarted" placeholder="请输入首发场次数" />
        </el-form-item>
        <el-form-item label="总上场分钟数" prop="Mins">
          <el-input v-model="form.Mins" placeholder="请输入总上场分钟数" />
        </el-form-item>
        <el-form-item label="场均得分" prop="Pointspg">
          <el-input v-model="form.Pointspg" placeholder="请输入场均得分" />
        </el-form-item>
        <el-form-item label="场均篮板" prop="Rebspg">
          <el-input v-model="form.Rebspg" placeholder="请输入场均篮板" />
        </el-form-item>
        <el-form-item label="场均助攻" prop="Assistspg">
          <el-input v-model="form.Assistspg" placeholder="请输入场均助攻" />
        </el-form-item>
        <el-form-item label="场均抢断" prop="Stealspg">
          <el-input v-model="form.Stealspg" placeholder="请输入场均抢断" />
        </el-form-item>
        <el-form-item label="场均盖帽" prop="Blockspg">
          <el-input v-model="form.Blockspg" placeholder="请输入场均盖帽" />
        </el-form-item>
        <el-form-item label="投篮命中率" prop="FgpctStr">
          <el-input v-model="form.FgpctStr" placeholder="请输入投篮命中率" />
        </el-form-item>
        <el-form-item label="三分命中率" prop="TppctStr">
          <el-input v-model="form.TppctStr" placeholder="请输入三分命中率" />
        </el-form-item>
        <el-form-item label="罚球命中率" prop="FtpctStr">
          <el-input v-model="form.FtpctStr" placeholder="请输入罚球命中率" />
        </el-form-item>
        <el-form-item label="进攻篮板总数" prop="Offrebs">
          <el-input v-model="form.Offrebs" placeholder="请输入进攻篮板总数" />
        </el-form-item>
        <el-form-item label="防守篮板总数" prop="Defrebs">
          <el-input v-model="form.Defrebs" placeholder="请输入防守篮板总数" />
        </el-form-item>
        <el-form-item label="失误次数" prop="Turnovers">
          <el-input v-model="form.Turnovers" placeholder="请输入失误次数" />
        </el-form-item>
        <el-form-item label="犯规次数" prop="Fouls">
          <el-input v-model="form.Fouls" placeholder="请输入犯规次数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStats, getStats, delStats, addStats, updateStats } from "@/api/system/stats"

export default {
  name: "Stats",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 球队统计信息表格数据
      statsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        Season: null,
        Teamid: null,
        LogoDark: null,
        LogoLight: null,
        teamname: null,
        Games: null,
        Gamesstarted: null,
        Mins: null,
        Pointspg: null,
        Rebspg: null,
        Assistspg: null,
        Stealspg: null,
        Blockspg: null,
        FgpctStr: null,
        TppctStr: null,
        FtpctStr: null,
        Offrebs: null,
        Defrebs: null,
        Turnovers: null,
        Fouls: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        Season: [
          { required: true, message: "赛季，例如 2023-24不能为空", trigger: "blur" }
        ],
        Teamid: [
          { required: true, message: "球队唯一ID不能为空", trigger: "blur" }
        ],
        teamname: [
          { required: true, message: "球队名称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询球队统计信息列表 */
    getList() {
      this.loading = true
      listStats(this.queryParams).then(response => {
        this.statsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        Season: null,
        Teamid: null,
        LogoDark: null,
        LogoLight: null,
        teamname: null,
        Games: null,
        Gamesstarted: null,
        Mins: null,
        Pointspg: null,
        Rebspg: null,
        Assistspg: null,
        Stealspg: null,
        Blockspg: null,
        FgpctStr: null,
        TppctStr: null,
        FtpctStr: null,
        Offrebs: null,
        Defrebs: null,
        Turnovers: null,
        Fouls: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加球队统计信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStats(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改球队统计信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStats(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStats(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除球队统计信息编号为"' + ids + '"的数据项？').then(function() {
        return delStats(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/stats/export', {
        ...this.queryParams
      }, `stats_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
