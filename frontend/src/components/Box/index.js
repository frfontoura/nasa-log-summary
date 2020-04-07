import React from "react";

export default function Box({ title, isCentralized, children}) {
  const style = "box-content" + (isCentralized ? " box-content-center" : "" );

  return (
    <div className="box">
      <h3>{title}</h3>
      <div className={style}>
        {children}
      </div>
    </div>
  );
}
