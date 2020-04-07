import React, { useState } from "react";

import Info from "./components/Info";
import Box from "./components/Box";
import Top5Table from "./components/Top5Table";
import NotFoundByDayTable from "./components/NotFoundByDayTable";

import api from "./services/api";

import "./app.css";
import Loading from "./components/Loading";

const INITIAL_STATE = {
  uniqueHosts: 0,
  totalSize: 0,
  notFoundCount: 0,
  top5NotFound: [],
  notFoundByDay: [],
};

function App() {
  const [data, setData] = useState(INITIAL_STATE);
  const [processing, setProcessing] = useState(false);

  async function process() {
    setProcessing(true);
    try {
      const resp = await api.get("/logs/process");
      setData(resp.data);
    } catch (error) {
      alert(
        "Ocorreu algum problema ao processar, verifique o log da aplicação"
      );
      console.error(error);
    }
    setProcessing(false);
  }

  return (
    <>
      <Loading visible={processing}/>
      <div className="container">
        <Info handleButtonClick={process} />

        <div className="box-section">
          <Box title="Hosts Únicos" isCentralized>
            <span>{data?.uniqueHosts}</span>
          </Box>

          <Box title="Total Not Found (404)" isCentralized>
            <span>{data?.notFoundCount}</span>
          </Box>

          <Box title="Total Bytes" isCentralized>
            <span>{data?.totalSize}</span>
          </Box>
        </div>

        <div className="box-section">
          <Top5Table data={data?.top5NotFound} />
          <NotFoundByDayTable data={data?.notFoundByDay} />
        </div>
      </div>
    </>
  );
}

export default App;
