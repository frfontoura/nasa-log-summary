import React from "react";
import Box from "../Box";

export default function components({ data }) {
  return (
    <Box title="Not Found (404) por dia">
      <table>
        <thead>
          <tr>
            <th>Data</th>
            <th>Qtd Not Found (404)</th>
          </tr>
        </thead>
        <tbody>
          {data.map((d) => (
            <tr key={d._1}>
              <td>{d._1}</td>
              <td>{d._2}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </Box>
  );
}
