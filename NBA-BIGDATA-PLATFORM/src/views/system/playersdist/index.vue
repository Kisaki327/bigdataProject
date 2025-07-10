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

    <el-divider />

    <el-card class="chart-card">
      <div slot="header" class="card-title">
        {{ seasonYear }} PER-场均得分关系图
      </div>
      <div class="chart-box" id="ScatterChart"></div>
    </el-card>

    <el-card class="chart-card">
      <div slot="header" class="card-title">
        {{ seasonYear }} 球员场均得分区间分布
      </div>
      <div class="chart-box" id="FunnelChart"></div>
    </el-card>
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
      yearOptions: Array.from({ length: 22 }, (_, i) => (2003 + i).toString()),
      scatterOption: {},
      funnelOption: {},
      isDark: false,
      scatterChart: null,
      funnelChart: null
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
      const scatterData = this.players.map(p => [p.per, p.pointSpg]);
      this.scatterOption = {
        title: {
          text: `${this.seasonYear}` + ' PER-场均得分关系图',
          left: 'center',
          show: false
        },
        toolbox: {
          show: true,
          orient: 'horizontal',
          left: 'right',
          top: 'top',
          itemSize: 20,
          iconStyle: {
            borderColor: '#333'
          },
          feature: {
            saveAsImage: {
              show: true,
              title: '保存图片'
            },
            dataView: {
              show: true,
              title: '查看数据',
              readOnly: true
            },
            restore: {
              show: true,
              title: '还原'
            },
            dataZoom: {
              show: true
            }
          }
        },
        tooltip: { trigger: 'item', formatter: p => `PER: ${p.value[0]}<br>得分: ${p.value[1]}` },
        xAxis: { name: 'PER', type: 'value' },
        yAxis: { name: '场均得分', type: 'value' },
        series: [{
          name: '球员',
          type: 'scatter',
          symbolSize: 10,
          data: scatterData,
          itemStyle: { color: '#3BA272' }
        }]
      };

      const funnelData = this.processFunnelData();
      this.funnelOption = {
        title: {
          text: `${this.seasonYear}` + ' 球员场均得分区间分布',
          left: 'center',
          show: false
        },
        toolbox: {
          show: true,
          orient: 'horizontal',
          left: 'right',
          top: 'top',
          itemSize: 20,
          iconStyle: {
            borderColor: '#333'
          },
          feature: {
            saveAsImage: {
              show: true,
              title: '保存图片'
            },
            dataView: {
              show: true,
              title: '查看数据',
              readOnly: true
            },
            restore: {
              show: true,
              title: '还原'
            },
            dataZoom: {
              show: true
            }
          }
        },
        tooltip: { trigger: 'item', formatter: '{b}<br/>人数: {c}' },
        series: [{
          name: '得分分布',
          type: 'funnel',
          left: '10%',
          top: 20,
          bottom: 20,
          width: '80%',
          sort: 'descending',
          label: { show: true, position: 'inside' },
          data: funnelData
        }]
      };

      this.$store.dispatch('chart/setChart', {
        id: this.seasonYear + ' PER-场均得分关系图',
        option: this.scatterOption,
        type: 'scatter'
      });
      this.$store.dispatch('chart/setChart', {
        id: this.seasonYear + ' 球员场均得分区间分布',
        option: this.funnelOption,
        type: 'funnel'
      });
    },
    drawChart() {
      if (this.scatterChart) this.scatterChart.dispose();
      if (this.funnelChart) this.funnelChart.dispose();

      this.scatterChart = echarts.init(document.getElementById("ScatterChart"), this.isDark ? "dark" : "light");
      this.funnelChart = echarts.init(document.getElementById("FunnelChart"), this.isDark ? "dark" : "light");

      this.scatterChart.setOption(this.scatterOption);
      this.funnelChart.setOption(this.funnelOption);

      window.addEventListener('resize', () => {
        this.scatterChart.resize();
        this.funnelChart.resize();
      });
    },
    updateChart() {
      this.loadData();
    }
  },
  beforeDestroy() {
    this.$store.dispatch('chart/clearCharts')
  }
};
</script>

<style scoped>
.app-container {
   min-height: 100vh;
  background-color: rgba(0, 10, 30, 0.85);
  background-image: linear-gradient(45deg, rgba(255,255,255,0.05) 25%, transparent 25%),
                    linear-gradient(-45deg, rgba(255,255,255,0.05) 25%, transparent 25%),
                    linear-gradient(45deg, transparent 75%, rgba(255,255,255,0.05) 75%),
                    linear-gradient(-45deg, transparent 75%, rgba(255,255,255,0.05) 75%);
  background-size: 40px 40px;
  background-position: 0 0, 0 20px, 20px -20px, -20px 0px;
  background-attachment: fixed;
  padding: 20px;
}
.chart-card {
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.card-title {
  text-align: center;
  font-weight: bold;
  font-size: 16px;
  color: #333;
}
.chart-box {
 flex: 1 1 calc(50% - 20px);
  height: 45vh;
  min-width: 400px;
  background-color: rgba(10, 10, 30, 0.85);
  border-radius: 12px;
  padding: 10px;
  box-shadow: 0 0 12px rgba(0, 255, 255, 0.12);
  transition: transform 0.3s ease;
  position: relative;
}
.chart-box:hover {
  transform: scale(1.02);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.3);
}
</style>
