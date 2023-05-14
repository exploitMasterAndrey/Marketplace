import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { BASE_URL } from "../../utils/constants";

export const createUser = createAsyncThunk(
  "users/createUser",
  async (payload, thunkAPI) => {
    try {
      await axios.post(`${BASE_URL}/auth/register`, payload);
      const res = await axios.post(`${BASE_URL}/auth/login`, {email: payload.email, password: payload.password});
      console.log(res.data);
      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

export async function getAllOrders(userId){
  const res = await axios(`${BASE_URL}/orders/getAll/${userId}`);
  console.log(res);
  return res.data;
}

export const loginUser = createAsyncThunk(
  "users/loginUser",
  async (payload, thunkAPI) => {
    try {
      const res = await axios.post(`${BASE_URL}/auth/login`, payload);
      console.log(res.data);

      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

export const updateUser = createAsyncThunk(
  "users/updateUser",
  async (payload, thunkAPI) => {
    try {
      const res = await axios.put(`${BASE_URL}/profile/update`, payload);
      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

export const createOrder = createAsyncThunk(
  "users/createOrder",
  async (payload, thunkAPI) => {
    try {
      console.log(payload)
      const res = await axios.post(`${BASE_URL}/orders/create`, payload);
      return res.data;
    } catch (err) {
      console.log(err);
      return thunkAPI.rejectWithValue(err);
    }
  }
);

const currentUser = localStorage.getItem("currentUser") !== null
? JSON.parse(localStorage.getItem("currentUser")) : [];

const addCurrentUser = (state, { payload }) => {
  localStorage.setItem("currentUser", JSON.stringify(payload));
  state.currentUser = payload;
};

const clearCart = (state, { payload }) => {
  state.cart = [];
};


const userSlice = createSlice({
  name: "user",
  initialState: {
    currentUser: currentUser,
    cart: [],
    isLoading: false,
    formType: "signup",
    showForm: false,
  },
  reducers: {
    addItemToCart: (state, { payload }) => {
      let newCart = [...state.cart];
      const found = state.cart.find(({ id }) => id === payload.id);

      if (found) {
        newCart = newCart.map((item) => {
          return item.id === payload.id
            ? { ...item, quantity: payload.quantity || item.quantity + 1 }
            : item;
        });
      } else newCart.push({ ...payload, quantity: 1 });

      state.cart = newCart;
    },
    removeItemFromCart: (state, { payload }) => {
      state.cart = state.cart.filter(({ id }) => id !== payload);
    },
    toggleForm: (state, { payload }) => {
      state.showForm = payload;
    },
    toggleFormType: (state, { payload }) => {
      state.formType = payload;
    },
    logout: (state, { payload }) => {
      state.currentUser = null;
      state.cart = [];
      state.isLoading = false;
      state.formType = "signup";
      state.showForm = false;
      localStorage.setItem("currentUser", null);
    }
  },
  extraReducers: (builder) => {
    builder.addCase(createUser.fulfilled, addCurrentUser);
    builder.addCase(loginUser.fulfilled, addCurrentUser);
    builder.addCase(updateUser.fulfilled, addCurrentUser);
    builder.addCase(createOrder.fulfilled, clearCart);
  },
});

export const { addItemToCart, removeItemFromCart, toggleForm, toggleFormType, logout} =
  userSlice.actions;

export default userSlice.reducer;
