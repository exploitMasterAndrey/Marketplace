import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

import { buildUrl } from "../../utils/common";
import { BASE_URL } from "../../utils/constants";

export const apiSlice = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({ baseUrl: BASE_URL }),
  tagTypes: ["Product"],
  endpoints: (builder) => ({
    getProduct: builder.query({
      query: ({ id }) => `/products/single/id/${id}`,
      providesTags: ["Product"],
    }),
    getProductByTitle: builder.query({
      query: ({ title }) => `/products/single/title/${title}`,
      providesTags: ["ProductByTitle"],
    }),
    getProducts: builder.query({
      query: (params) => buildUrl("/products", params),
      providesTags: ["Products"],
    }),
  }),
});



export const { useGetProductQuery, useGetProductsQuery, useGetProductByTitleQuery } = apiSlice;
