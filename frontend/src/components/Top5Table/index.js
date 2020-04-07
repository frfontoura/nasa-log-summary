import React from "react";
import Box from "../Box";

export default function Top5Table({ data }) {
  return (
    <Box title="Top 5 URL">
      <table>
        <thead>
          <tr>
            <th>Host</th>
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
