export function extractChartSummary(option) {
  if (!option || typeof option !== 'object') return '无效的 ECharts 配置';
      console.log(option);
  const result = {
    图表类型: new Set(),
    标题: option.title?.text || option.title[0].text || '无标题',
    数据系列: [],
    坐标轴: {},
  };

  const seriesArr = Array.isArray(option.series) ? option.series : [option.series];

  seriesArr.forEach((series, idx) => {
    const type = series.type || 'unknown';
    result.图表类型.add(type);

    let seriesInfo = {
      名称: series.name || `系列${idx + 1}`,
      类型: type,
      数据量: Array.isArray(series.data) ? series.data.length : 0,
      示例数据: [],
      额外信息: {}
    };

    switch(type) {
      case 'bar':
      case 'line': {
        // 提取xAxis,yAxis名称，示例数据
        seriesInfo.数据 = series.data ? series.data : [];
        if (option.xAxis?.name) result.坐标轴.x轴名称 = option.xAxis.name;
        if (option.yAxis?.name) result.坐标轴.y轴名称 = option.yAxis.name;
        if (option.xAxis[0]?.name) result.坐标轴.x轴名称 = option.xAxis[0].name;
        if (option.yAxis[0]?.name) result.坐标轴.y轴名称 = option.yAxis[0].name;
        break;
      }
      case 'scatter': {
        // 散点图示例数据（x,y坐标）
        seriesInfo.数据 = series.data ? series.data.slice(0, 300) : [];
        if (option.xAxis?.name) result.坐标轴.x轴名称 = option.xAxis.name;
        if (option.yAxis?.name) result.坐标轴.y轴名称 = option.yAxis.name;
        break;
      }
      case 'funnel': {
        // 漏斗图，数据一般为对象数组，包含name,value
        if (Array.isArray(series.data)) {
          seriesInfo.数据 = series.data.map(item => {
            if (typeof item === 'object' && item !== null) {
              return { name: item.name, value: item.value };
            }
            return item;
          });
        }
        break;
      }
      case 'radar': {
        // 雷达图通常有indicator，series.data是数组嵌套数据
        seriesInfo.额外信息.indicator = option.radar?.indicator || [];
        if (series.data && Array.isArray(series.data)) {
          seriesInfo.示例数据 = series.data.map(item => {
            if (item.value) return item.value;
            return item;
          });
        }
        break;
      }
      default: {
        seriesInfo.示例数据 = series.data ? series.data : [];
      }
    }

    result.数据系列.push(seriesInfo);
  });

  // 把 Set 转成 Array
  result.图表类型 = Array.from(result.图表类型);

  return result;
}
