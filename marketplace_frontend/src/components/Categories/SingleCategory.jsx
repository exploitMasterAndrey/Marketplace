import React from "react";

import Category from "./Category";
import Poster from "../Poster/Poster";
import Sidebar from "../Sidebar/Sidebar";

const SingleCategory = () => {
  return (
    <>
      <Sidebar />
      <Poster />
      <Category />
    </>
  );
};

export default SingleCategory;
