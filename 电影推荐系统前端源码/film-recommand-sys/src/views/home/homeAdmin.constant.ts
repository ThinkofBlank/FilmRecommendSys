import * as echart from "echarts";

export type DataType = {
  name: string;
  value: number;
};

export const useFilmsChart = (dom: HTMLElement) => {
  const myChart = echart.init(dom);

  const options = {
    tooltip: {
      trigger: "item",
      confine: true,
      formatter: "{a} <br/>{b} : {c} ({d}%)",
    },
    series: [
      {
        name: "素材统计",
        type: "pie",
        radius: [30, 100],
        center: ["50%", "50%"],
        roseType: "area",
        itemStyle: {
          borderRadius: 2,
        },
        data: [] as DataType[],
      },
    ],
  };

  return (data: DataType[]) => {
    options.series[0].data = data;
    myChart.setOption(options);
  };
};

export const useFilmsTop10Chart = (dom: HTMLElement) => {
  const myChart = echart.init(dom);

  const options = {
    grid: {
      x: 45,
      y: 20,
      x2: 5,
      y2: 110,
    },
    tooltip: {
      trigger: "item",
      confine: true,
      formatter: "{a} <br/>{b} : {c}",
    },
    xAxis: {
      type: "category",
      data: [] as string[],
      axisLabel: {
        interval: 0, // 代表显示所有x轴标签显示
        rotate: 60, // 代表逆时针旋转45度
      },
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        name: "评分TOP 10",
        data: [] as number[],
        type: "bar",
        showBackground: true,
        backgroundStyle: {
          color: "rgba(180, 180, 180, 0.2)",
        },
      },
    ],
  };

  return (data: DataType[]) => {
    options.xAxis.data = data.map((d) => d.name);
    options.series[0].data = data.map((d) => d.value);
    myChart.setOption(options);
  };
};
