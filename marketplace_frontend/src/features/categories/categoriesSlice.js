import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { BASE_URL } from "../../utils/constants";

export const getCategories = createAsyncThunk(
  "categories/getCategories",
  async (_, thunkAPI) => {
    try {
      const res = await axios(`${BASE_URL}/categories`);
      console.log(res.data);
      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

export const createOrUpdateCategory = createAsyncThunk(
  "products/createOrUpdateCategory",
  async (categoryParams, thunkAPI) => {
    try {
      const res = await axios.post(`${BASE_URL}/categories/createOrUpdate`, categoryParams);
      console.log(res.data);
      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

const categoriesSlice = createSlice({
  name: "categories",
  initialState: {
    list: [],
    isLoading: false,
  },
  extraReducers: (builder) => {
    builder.addCase(getCategories.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getCategories.fulfilled, (state, { payload }) => {
      state.list = payload;
      state.isLoading = false;
    });
    builder.addCase(getCategories.rejected, (state) => {
      state.isLoading = false;
    });
    builder.addCase(createOrUpdateCategory.fulfilled, (state, { payload }) => {
      state.list = state.list.filter((item) => item.id !== payload.id); 
      state.list.push(payload);
    });
  },
});

export default categoriesSlice.reducer;
