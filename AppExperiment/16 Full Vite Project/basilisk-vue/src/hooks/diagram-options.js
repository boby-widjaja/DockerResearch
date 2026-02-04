const formattingNumber = number => {
    let formattedNumber = Number(number).toFixed(2).toString();
    let [integerPart, decimalPart] = formattedNumber.split(".");
    let integerWithCommas = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    return decimalPart ? integerWithCommas + "," + decimalPart : integerWithCommas;
}

export const useAreaOptions = ({labels=[], name='', data=[]}) => {
    return {
        options:{
            labels,
            yaxis: {
                opposite: true,
                labels: {formatter: formattingNumber}
            },
            stroke: {curve: 'smooth'},
            dataLabels: {enabled: false},
            colors: ['#14acf8'],
            chart: {
                foreColor: '#9badd5',
                zoom: {enabled: false}
            },
        },
        series:[{
            name,
            data
        }]
    };
}

export const usePieOptions = (labels = []) => {
    return {
        chart: {foreColor: '#9badd5'},
        labels
    };
}

export const useBarOptions = ({categories = [], data = []}) => {
    return {
        options: {
            colors: ['#14acf8'],
            chart: {foreColor: '#9badd5',},
            plotOptions: {
                bar: {
                    borderRadius: 4,
                    borderRadiusApplication: 'end',
                    horizontal: false,
                }
            },
            dataLabels: {enabled: false},
            xaxis: {categories}
        },
        series: [{data}]
    };
}

export const useRadarOptions = ({categories = [], name = '', data = []}) => {
    return {
        options: {
            chart: {foreColor: '#7f8c8d'},
            dataLabels: {enabled: true},
            plotOptions: {
                radar: {
                    size: 200,
                    polygons: {
                        strokeColors: '#74b9ff',
                        fill: {colors: ['#f1f2f6', '#ffffff']}
                    }
                }
            },
            colors: ['#14acf8'],
            markers: {
                size: 4,
                colors: ['#fff'],
                strokeColor: '#14acf8',
                strokeWidth: 2,
            },
            tooltip: {
                y: {
                    formatter: function(val) {
                        return val
                    }
                }
            },
            xaxis: {categories},
            yaxis: {
                tickAmount: 7,
                labels: {
                    formatter: function(val, i) {
                        if (i % 2 === 0) {
                            return val
                        } else {
                            return ''
                        }
                    }
                }
            }
        },
        series:[{name,data}]
    };
}