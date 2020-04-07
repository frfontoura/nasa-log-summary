import React from "react";

export default function Info({ handleButtonClick }) {
  return (
    <div className="info-section">
      <div>
        <h3>Antes de começar...</h3>
        <p>
          Para processar e obter os dados resumidos dos logs, verifique se
          existe algum arquivo com a extensão .gz na pasta /data.
        </p>
        <p>Você pode fazer o download dos arquivos clicando nos links:</p>
        <ul>
          <li>
            <a href="ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz">
              NASA_access_log_Jul95.gz
            </a>
          </li>
          <li>
            <a href="ftp://ita.ee.lbl.gov/traces/NASA_access_log_Aug95.gz">
              NASA_access_log_Aug95.gz
            </a>
          </li>
        </ul>
      </div>
      <button onClick={handleButtonClick}>Processar</button>
    </div>
  );
}
