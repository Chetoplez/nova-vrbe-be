const CharacterStatusStats = ({ stats }) =>
    <div className="w3-container">
        {stats && stats.map((stat, index) =>
            <div key={index} className="caratteristiche">
                <div className="w3-half caratteristiche-totale">
                    <span>{(stat.baseStat) + (stat.modified)}</span>
                </div>
                <div className="w3-half car">
                    <h6 style={{ textTransform: "lowercase" }}><strong>{stat.statName}</strong></h6>
                    <p className="margin-none">Base: {stat.baseStat}</p>
                    <p className="margin-none">
                        Mod:
                        <span style={{ color: stat.modified > 0 ? '#0BA400' : '#A40000' }}> {stat.modified}</span>
                    </p>
                </div>
            </div>
        )}
    </div>

export default CharacterStatusStats