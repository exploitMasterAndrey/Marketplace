import React from "react";
import { Route, Routes } from "react-router-dom";

import { ROUTES } from "../../utils/routes";

import Home from "../Home/Home";
import SingleProduct from "../Products/SingleProduct";
import Profile from "../Profile/Profile";
import SingleCategory from "../Categories/SingleCategory";
import Cart from "../Cart/Cart";
import ProductM from "../Products/ProductM";
import CategorytM from "../Categories/CategoryM";
import SellerM from "../User/SellerM";
import Orders from "../User/Orders";

const AppRoutes = () => (
  <Routes>
    <Route index element={<Home />} />
    <Route path={ROUTES.PRODUCT} element={<SingleProduct />} />
    <Route path={ROUTES.PROFILE} element={<Profile />} />
    <Route path={ROUTES.PRODUCTM} element={<ProductM />} />
    <Route path={ROUTES.CATEGORYM} element={<CategorytM />} />
    <Route path={ROUTES.SELLERM} element={<SellerM />} />
    <Route path={ROUTES.CATEGORY} element={<SingleCategory />} />
    <Route path={ROUTES.CART} element={<Cart />} />
    <Route path={ROUTES.ORDERS} element={<Orders />} />
  </Routes>
);

export default AppRoutes;
