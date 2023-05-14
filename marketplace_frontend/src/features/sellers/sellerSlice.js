import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { BASE_URL } from "../../utils/constants";

export const getSellers = createAsyncThunk(
  "categories/getSellers",
  async (_, thunkAPI) => {
    try {
      const res = await axios(`${BASE_URL}/profile/getSellers`);
      console.log(res.data);
      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

export const createOrUpdateSeller = createAsyncThunk(
  "products/createOrUpdateSeller",
  async (sellerParams, thunkAPI) => {
    try {
      const res = await axios.post(`${BASE_URL}/profile/createOrUpdateSeller`, sellerParams);
      console.log(res.data);
      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

export const deleteSeller = createAsyncThunk(
    "products/deleteSeller",
    async (sellerId, thunkAPI) => {
      try {
        const res = await axios.delete(`${BASE_URL}/profile/delete/${sellerId}`);
        return res.data;
      } catch (err) {
        console.log(err);
        return thunkAPI.rejectWithValue(err);
      }
    }
  );

const sllerSlice = createSlice({
  name: "sellers",
  initialState: {
    list: []
  },
  extraReducers: (builder) => {
    builder.addCase(getSellers.fulfilled, (state, { payload }) => {
      state.list = payload;
      state.isLoading = false;
    });
    builder.addCase(createOrUpdateSeller.fulfilled, (state, { payload }) => {
      state.list = state.list.filter((item) => item.id !== payload.id); 
      state.list.push(payload);
    });
    builder.addCase(deleteSeller.fulfilled, (state, { payload }) => {
        state.list = state.list.filter((item) => item.id !== payload.id); 
      });
  },
});

export default sllerSlice.reducer;
