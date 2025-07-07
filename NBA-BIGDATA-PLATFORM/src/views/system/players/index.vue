<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker clearable
          v-model="queryParams.birthDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出生日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="国家/地区" prop="country">
        <el-input
          v-model="queryParams.country"
          placeholder="请输入国家/地区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="显示名称" prop="displayName">
        <el-input
          v-model="queryParams.displayName"
          placeholder="请输入显示名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="选秀年份" prop="draftSeason">
        <el-input
          v-model="queryParams.draftSeason"
          placeholder="请输入选秀年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="NBA经验" prop="experience">
        <el-input
          v-model="queryParams.experience"
          placeholder="请输入NBA经验"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中文名字" prop="firstName">
        <el-input
          v-model="queryParams.firstName"
          placeholder="请输入中文名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="英文名字" prop="firstNameEn">
        <el-input
          v-model="queryParams.firstNameEn"
          placeholder="请输入英文名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身高" prop="height">
        <el-input
          v-model="queryParams.height"
          placeholder="请输入身高"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身高" prop="heightMetric">
        <el-input
          v-model="queryParams.heightMetric"
          placeholder="请输入身高"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球衣号码" prop="jerseyNo">
        <el-input
          v-model="queryParams.jerseyNo"
          placeholder="请输入球衣号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中文姓氏" prop="lastName">
        <el-input
          v-model="queryParams.lastName"
          placeholder="请输入中文姓氏"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="英文姓氏" prop="lastNameEn">
        <el-input
          v-model="queryParams.lastNameEn"
          placeholder="请输入英文姓氏"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球员代码" prop="playerCode">
        <el-input
          v-model="queryParams.playerCode"
          placeholder="请输入球员代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球员位置" prop="position">
        <el-input
          v-model="queryParams.position"
          placeholder="请输入球员位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退役年份" prop="retireYear">
        <el-input
          v-model="queryParams.retireYear"
          placeholder="请输入退役年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="新秀赛季" prop="rookieSeason">
        <el-input
          v-model="queryParams.rookieSeason"
          placeholder="请输入新秀赛季"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="毕业学校" prop="school">
        <el-input
          v-model="queryParams.school"
          placeholder="请输入毕业学校"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="赛季" prop="season">
        <el-input
          v-model="queryParams.season"
          placeholder="请输入赛季"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始职业生涯年份" prop="startYear">
        <el-input
          v-model="queryParams.startYear"
          placeholder="请输入开始职业生涯年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队缩写" prop="teamAbbr">
        <el-input
          v-model="queryParams.teamAbbr"
          placeholder="请输入球队缩写"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队所在城市" prop="teamCity">
        <el-input
          v-model="queryParams.teamCity"
          placeholder="请输入球队所在城市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属球队ID" prop="teamId">
        <el-input
          v-model="queryParams.teamId"
          placeholder="请输入所属球队ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队深色Logo链接" prop="teamLogoDark">
        <el-input
          v-model="queryParams.teamLogoDark"
          placeholder="请输入球队深色Logo链接"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队浅色Logo链接" prop="teamLogoLight">
        <el-input
          v-model="queryParams.teamLogoLight"
          placeholder="请输入球队浅色Logo链接"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="球队中文名" prop="teamName">
        <el-input
          v-model="queryParams.teamName"
          placeholder="请输入球队中文名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="体重" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入体重"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="体重" prop="weightMetric">
        <el-input
          v-model="queryParams.weightMetric"
          placeholder="请输入体重"
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
          v-hasPermi="['system:players:add']"
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
          v-hasPermi="['system:players:edit']"
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
          v-hasPermi="['system:players:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:players:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="playersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="球员ID" align="center" prop="playerId" />
      <el-table-column label="头像URL" align="center" prop="avatar" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.avatar" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="出生日期" align="center" prop="birthDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="国家/地区" align="center" prop="country" />
      <el-table-column label="显示名称" align="center" prop="displayName" />
      <el-table-column label="选秀年份" align="center" prop="draftSeason" />
      <el-table-column label="NBA经验" align="center" prop="experience" />
      <el-table-column label="中文名字" align="center" prop="firstName" />
      <el-table-column label="英文名字" align="center" prop="firstNameEn" />
      <el-table-column label="半身大图URL" align="center" prop="halfPhotoBig" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.halfPhotoBig" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="身高" align="center" prop="height" />
      <el-table-column label="身高" align="center" prop="heightMetric" />
      <el-table-column label="球衣号码" align="center" prop="jerseyNo" />
      <el-table-column label="中文姓氏" align="center" prop="lastName" />
      <el-table-column label="英文姓氏" align="center" prop="lastNameEn" />
      <el-table-column label="球员代码" align="center" prop="playerCode" />
      <el-table-column label="球员位置" align="center" prop="position" />
      <el-table-column label="退役年份" align="center" prop="retireYear" />
      <el-table-column label="新秀赛季" align="center" prop="rookieSeason" />
      <el-table-column label="毕业学校" align="center" prop="school" />
      <el-table-column label="赛季" align="center" prop="season" />
      <el-table-column label="开始职业生涯年份" align="center" prop="startYear" />
      <el-table-column label="球队缩写" align="center" prop="teamAbbr" />
      <el-table-column label="球队所在城市" align="center" prop="teamCity" />
      <el-table-column label="所属球队ID" align="center" prop="teamId" />
      <el-table-column label="球队深色Logo链接" align="center" prop="teamLogoDark" />
      <el-table-column label="球队浅色Logo链接" align="center" prop="teamLogoLight" />
      <el-table-column label="球队中文名" align="center" prop="teamName" />
      <el-table-column label="体重" align="center" prop="weight" />
      <el-table-column label="体重" align="center" prop="weightMetric" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:players:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:players:remove']"
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

    <!-- 添加或修改NBA球员基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="头像URL" prop="avatar">
          <image-upload v-model="form.avatar"/>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker clearable
            v-model="form.birthDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="国家/地区" prop="country">
          <el-input v-model="form.country" placeholder="请输入国家/地区" />
        </el-form-item>
        <el-form-item label="显示名称" prop="displayName">
          <el-input v-model="form.displayName" placeholder="请输入显示名称" />
        </el-form-item>
        <el-form-item label="选秀年份" prop="draftSeason">
          <el-input v-model="form.draftSeason" placeholder="请输入选秀年份" />
        </el-form-item>
        <el-form-item label="NBA经验" prop="experience">
          <el-input v-model="form.experience" placeholder="请输入NBA经验" />
        </el-form-item>
        <el-form-item label="中文名字" prop="firstName">
          <el-input v-model="form.firstName" placeholder="请输入中文名字" />
        </el-form-item>
        <el-form-item label="英文名字" prop="firstNameEn">
          <el-input v-model="form.firstNameEn" placeholder="请输入英文名字" />
        </el-form-item>
        <el-form-item label="半身大图URL" prop="halfPhotoBig">
          <image-upload v-model="form.halfPhotoBig"/>
        </el-form-item>
        <el-form-item label="身高" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="身高" prop="heightMetric">
          <el-input v-model="form.heightMetric" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="球衣号码" prop="jerseyNo">
          <el-input v-model="form.jerseyNo" placeholder="请输入球衣号码" />
        </el-form-item>
        <el-form-item label="中文姓氏" prop="lastName">
          <el-input v-model="form.lastName" placeholder="请输入中文姓氏" />
        </el-form-item>
        <el-form-item label="英文姓氏" prop="lastNameEn">
          <el-input v-model="form.lastNameEn" placeholder="请输入英文姓氏" />
        </el-form-item>
        <el-form-item label="球员代码" prop="playerCode">
          <el-input v-model="form.playerCode" placeholder="请输入球员代码" />
        </el-form-item>
        <el-form-item label="球员位置" prop="position">
          <el-input v-model="form.position" placeholder="请输入球员位置" />
        </el-form-item>
        <el-form-item label="退役年份" prop="retireYear">
          <el-input v-model="form.retireYear" placeholder="请输入退役年份" />
        </el-form-item>
        <el-form-item label="新秀赛季" prop="rookieSeason">
          <el-input v-model="form.rookieSeason" placeholder="请输入新秀赛季" />
        </el-form-item>
        <el-form-item label="毕业学校" prop="school">
          <el-input v-model="form.school" placeholder="请输入毕业学校" />
        </el-form-item>
        <el-form-item label="赛季" prop="season">
          <el-input v-model="form.season" placeholder="请输入赛季" />
        </el-form-item>
        <el-form-item label="开始职业生涯年份" prop="startYear">
          <el-input v-model="form.startYear" placeholder="请输入开始职业生涯年份" />
        </el-form-item>
        <el-form-item label="球队缩写" prop="teamAbbr">
          <el-input v-model="form.teamAbbr" placeholder="请输入球队缩写" />
        </el-form-item>
        <el-form-item label="球队所在城市" prop="teamCity">
          <el-input v-model="form.teamCity" placeholder="请输入球队所在城市" />
        </el-form-item>
        <el-form-item label="所属球队ID" prop="teamId">
          <el-input v-model="form.teamId" placeholder="请输入所属球队ID" />
        </el-form-item>
        <el-form-item label="球队深色Logo链接" prop="teamLogoDark">
          <el-input v-model="form.teamLogoDark" placeholder="请输入球队深色Logo链接" />
        </el-form-item>
        <el-form-item label="球队浅色Logo链接" prop="teamLogoLight">
          <el-input v-model="form.teamLogoLight" placeholder="请输入球队浅色Logo链接" />
        </el-form-item>
        <el-form-item label="球队中文名" prop="teamName">
          <el-input v-model="form.teamName" placeholder="请输入球队中文名" />
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item label="体重" prop="weightMetric">
          <el-input v-model="form.weightMetric" placeholder="请输入体重" />
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
import { listPlayers, getPlayers, delPlayers, addPlayers, updatePlayers } from "@/api/system/players"

export default {
  name: "Players",
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
      // NBA球员基本信息表格数据
      playersList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        avatar: null,
        birthDate: null,
        country: null,
        displayName: null,
        draftSeason: null,
        experience: null,
        firstName: null,
        firstNameEn: null,
        halfPhotoBig: null,
        height: null,
        heightMetric: null,
        jerseyNo: null,
        lastName: null,
        lastNameEn: null,
        playerCode: null,
        position: null,
        retireYear: null,
        rookieSeason: null,
        school: null,
        season: null,
        startYear: null,
        teamAbbr: null,
        teamCity: null,
        teamId: null,
        teamLogoDark: null,
        teamLogoLight: null,
        teamName: null,
        weight: null,
        weightMetric: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询NBA球员基本信息列表 */
    getList() {
      this.loading = true
      listPlayers(this.queryParams).then(response => {
        this.playersList = response.rows
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
        playerId: null,
        avatar: null,
        birthDate: null,
        country: null,
        displayName: null,
        draftSeason: null,
        experience: null,
        firstName: null,
        firstNameEn: null,
        halfPhotoBig: null,
        height: null,
        heightMetric: null,
        jerseyNo: null,
        lastName: null,
        lastNameEn: null,
        playerCode: null,
        position: null,
        retireYear: null,
        rookieSeason: null,
        school: null,
        season: null,
        startYear: null,
        teamAbbr: null,
        teamCity: null,
        teamId: null,
        teamLogoDark: null,
        teamLogoLight: null,
        teamName: null,
        weight: null,
        weightMetric: null
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
      this.ids = selection.map(item => item.playerId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加NBA球员基本信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const playerId = row.playerId || this.ids
      getPlayers(playerId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改NBA球员基本信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.playerId != null) {
            updatePlayers(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPlayers(this.form).then(response => {
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
      const playerIds = row.playerId || this.ids
      this.$modal.confirm('是否确认删除NBA球员基本信息编号为"' + playerIds + '"的数据项？').then(function() {
        return delPlayers(playerIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/players/export', {
        ...this.queryParams
      }, `players_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
