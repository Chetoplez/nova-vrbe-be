import React from "react";

function CharacterStat({ stats, setStats, bucket, setBucket }) {
   

    const removePoint = (pos) => {
        let oldStats = [...stats];
        if (!(bucket + 1 > 16) && !(oldStats[pos].baseStat - 1 < 1)) {
            setBucket(cur => cur + 1);
            oldStats[pos].baseStat = oldStats[pos].baseStat - 1;
            setStats(oldStats)
        }

    }

    /*
     * Aggiunge un punto alle caratteristiche
     * @param  pos 
     */
    const addPoint = (pos) => {
        let oldStats = [...stats];
        if (!(bucket - 1 < 0) && !(oldStats[pos].baseStat + 1 > 16)) {
            setBucket(cur => cur - 1);
            oldStats[pos].baseStat = oldStats[pos].baseStat + 1;
            setStats(oldStats)
        }
    }

    return (
        <>
            <div>
                <div> Distribuisci i tuoi punti caratteristica prima di poter creare il tuo Personaggio:</div>
                <div>Ti rimangono: {bucket} punti</div>

                <section style={{width:'50%', margin:'auto'}}>
                    {stats.map((stat, index) => {
                        return (
                            <div key={index}>
                                <label style={{ textTransform: 'capitalize' }}><strong>{stat.statName}</strong></label>
                                <div style={{ display: 'flex', justifyContent: 'space-evenly' }}>
                                    <button style={{ borderRadius: '50%', width:'30px' }} type="button" onClick={() => { removePoint(index) }} >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
                                            viewBox="0 0 24 24" stroke="currentColor" stroke-width="2"
                                            style={{ width: '10px' }}>
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M20 12H4" />
                                        </svg>
                                    </button>
                                    <div><strong style={{fontSize: '21px'}}>{stat.baseStat}</strong> </div>
                                    <button style={{ borderRadius: '50%', width:'30px' }} type="button" onClick={() => { addPoint(index) }} >
                                        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
                                            viewBox="0 0 24 24" stroke="currentColor" stroke-width="2"
                                            style={{ width: '10px' }}>
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 4v16m8-8H4" />
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        )
                    })}

                </section>
            </div>
        </>
    )
} export default CharacterStat;