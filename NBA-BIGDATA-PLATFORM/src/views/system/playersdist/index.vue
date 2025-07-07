<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
        <el-select
        v-model="seasonYear"
        placeholder="选择赛季年份"
        style="width: 100%;"
        @change="updateChart"
        >
          <el-option
            v-for="item in yearOptions"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-switch
        v-model="isDark"
        active-text="暗色模式"
        inactive-text="亮色模式"
        @change="onThemeChange"
        />
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row :gutter="20">
      <el-col :span="10">
        <div class="chart-container">
          <div id="ScatterChart" style="height: 100%; width: 100%;"></div>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="chart-container">
          <div id="FunnelChart" style="height: 100%; width: 100%;"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { PlayerDist } from '@/api/system/players';
import * as echarts from 'echarts';

export default {
  data() {
    return {
      players: [],
      seasonYear: '2024',
      yearOptions: [
        '2003', '2004', '2005', '2006', '2007', '2008', '2009', '2010',
        '2011', '2012', '2013', '2014', '2015', '2016', '2017', '2018',
        '2019', '2020', '2021', '2022', '2023', '2024'
      ],
      scatterOption: {},
      funnelOption: {},
      isDark: false,
      scatterChart:null,
      funnelChart:null
    };
  },
  mounted() {
    this.loadData();

  },
  methods: {
    onThemeChange() {
      this.drawChart();
    },
    async loadData() {
      const res = await PlayerDist(this.seasonYear);
      this.players = res.data || [];
      this.setOptions();
      this.drawChart();
    },

    processFunnelData() {
      const bins = [
        { name: '0-5', min: 0, max: 5, count: 0 },
        { name: '5-10', min: 5, max: 10, count: 0 },
        { name: '10-15', min: 10, max: 15, count: 0 },
        { name: '15-20', min: 15, max: 20, count: 0 },
        { name: '20-25', min: 20, max: 25, count: 0 },
        { name: '25-30', min: 25, max: 30, count: 0 },
        { name: '30+', min: 30, max: Infinity, count: 0 },
      ];

      this.players.forEach(player => {
        const ppg = player.pointSpg;
        for (let bin of bins) {
          if (ppg >= bin.min && ppg < bin.max) {
            bin.count++;
            break;
          }
        }
      });

      const total = this.players.length;
      return bins.map(bin => ({
        name: `${bin.name} (${((bin.count / total) * 100).toFixed(1)}%)`,
        value: bin.count
      }));
    },

    setOptions() {
      // 散点图
      const scatterData = this.players.map(p => [p.per, p.pointSpg]);

      this.scatterOption = {
        title: {
          text: `${this.seasonYear} PER-场均得分关系图`,
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: (params) => `PER: ${params.value[0]}<br>pointSpg: ${params.value[1]}`
        },
        xAxis: {
          name: 'PER',
          type: 'value'
        },
        yAxis: {
          name: '场均得分',
          type: 'value'
        },
        series: [
          {
            name: '球员',
            type: 'scatter',
            symbolSize: 10,
            data: scatterData,
            itemStyle: {
              color: '#3BA272'
            }
          }
        ]
      };

      // 漏斗图
      const funnelData = this.processFunnelData();

      this.funnelOption = {
        title: {
          text: `${this.seasonYear} 球员场均得分区间分布`,
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>人数: {c}'
        },
        series: [
          {
            name: 'PER 区间',
            type: 'funnel',
            left: '10%',
            top: 60,
            bottom: 20,
            width: '80%',
            sort: 'descending',
            gap: 2,
            label: {
              show: true,
              position: 'inside'
            },
            data: funnelData
          }
        ]
      };
      this.$store.dispatch('chart/setChart',{
        id: this.scatterOption.title.text,
        option: this.scatterOption,
        type: 'scatter'
      })
      this.$store.dispatch('chart/setChart',{
        id: this.funnelOption.title.text,
        option: this.funnelOption,
        type: 'funnel'
      })
    },

    drawChart() {
      if (this.scatterChart) this.scatterChart.dispose();
      if (this.funnelChart) this.funnelChart.dispose();
      this.scatterChart = echarts.init(document.getElementById("ScatterChart"),this.isDark ? "dark" : "light");
      this.funnelChart = echarts.init(document.getElementById("FunnelChart"),this.isDark ? "dark" : "light");
      this.scatterChart.setOption(this.scatterOption);
      this.funnelChart.setOption(this.funnelOption);
      window.addEventListener('resize', () => {
        this.scatterChart.resize();
        this.funnelChart.resize();
      });
    },

    updateChart() {
      this.loadData(); // 切换赛季重新加载
    }
  },

  beforeDestroy() {
    this.$store.dispatch('chart/clearCharts')
  }
};
</script>


<style scoped>
.app-container {
  margin: 5px;
  padding: auto;
  width: 100%;
  height: 100%;
}
.chart-container {
  width: 100%;
  height: 500px;
}
</style>
