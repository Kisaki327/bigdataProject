<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>NBA 大数据分析平台</h2>
        <p>
          本平台致力于对 NBA 球赛相关数据进行收集、处理和可视化展示，涵盖球员表现分析、球队战绩对比、比赛结果预测等多个方面。
        </p>
        <p>
          通过前端图表组件与后端大数据服务的结合，帮助球迷、分析师和研究人员以更直观的方式洞察比赛趋势与球员表现。
        </p>
        <p>
          当前版本：<b>{{ version }}</b>
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
      </el-col>
    </el-row>

    <el-divider />

    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-user"></i> 项目负责人：何远超
            </p>
            <p>
              <i class="el-icon-message"></i> 邮箱：
              <el-link href="mailto:3022771982@qq.com">3022771982@qq.com</el-link>
            </p>
            <p>
              <i class="el-icon-link"></i> 项目地址：
              <el-link href="https://github.com/Kisaki327/bigdataProject" target="_blank">
                GitHub 项目仓库
              </el-link>
            </p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      version: "1.0.0"
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
    list-style-type: none;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
