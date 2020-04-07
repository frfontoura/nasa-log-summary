import React from "react";
import ReactLoading from "react-loading";

export default function Loading({ visible }) {
  return visible ? (
    <div className="loading">
      <ReactLoading type="bars" color="#04d361" height={200} width={200} />
      <h1>Aguarde enquanto os arquivos são processados.</h1>
      <h1>Isso pode demorar...</h1>
    </div>
  ) : null;
}
