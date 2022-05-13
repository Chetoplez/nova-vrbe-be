import React from "react";

import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from 'chart.js';
import { Radar } from 'react-chartjs-2';

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
); 

function StatChart({ stats }){
    const data = {
        labels: stats.map(car=> car.statName),
        datasets: [
          {
            label: 'Orientamento Personaggio',
            data: stats.map(car=> car.baseStat),
            backgroundColor: '#ffbb0087',
            borderColor: '#554641',
            borderWidth: 2,
          },
        ],
      };
    
      const options = {
        scales: {
            r: {
                angleLines: {
                    display: false
                },
                suggestedMin: 1,
                suggestedMax: 10
            }
        }
    };


    return(
        <div>
            
             <Radar data={data} options={options} /> 
        </div>

        
    )
}export default StatChart;