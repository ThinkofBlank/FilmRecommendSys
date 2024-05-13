import * as echart from "echarts";

export type DataType = {
  name: string;
  value: number;
};

export const useHobbyChart = (dom: HTMLElement) => {
  const myChart = echart.init(dom);

  const options = {
    tooltip: {
      trigger: "item",
    },
    series: [
      {
        name: "Access From",
        type: "pie",
        radius: "50%",
        data: [] as DataType[],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  };

  return (data: DataType[]) => {
    options.series[0].data = data;
    myChart.setOption(options);
  };
};
