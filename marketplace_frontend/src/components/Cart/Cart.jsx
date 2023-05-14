import React, { useState } from "react";
import Sidebar from "../Sidebar/Sidebar";
import { useDispatch, useSelector} from "react-redux";
import {
  addItemToCart,
  removeItemFromCart
} from "../../features/user/userSlice";
import { createOrder } from "../../features/user/userSlice";

import styles from "../../styles/Cart.module.css";
import stylesProd from "../../styles/Profile.module.css";
import { sumBy } from "../../utils/common";

const Cart = () => {
  const dispatch = useDispatch();
  const { cart, currentUser } = useSelector(({ user }) => user);

  const changeQuantity = (item, quantity) => {
    dispatch(addItemToCart({ ...item, quantity }));
  };

  const removeItem = (id) => {
    dispatch(removeItemFromCart(id));
  };


  const initialValues = {
    consumerId: currentUser.id,
    consumerPhone: "",
    consumerAddress: "",
    products: cart.map(({ id, quantity }) => ({ id, quantity })),
  }

  const [values, setValues] = useState(initialValues);

  const handleChange = ({ target: { value, name } }) => {
    setValues({ ...values, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // setValues({ ...values, ["products"]: cart });
    dispatch(createOrder(values))
    setValues(initialValues);
  }; 

  return (
    <>
    <Sidebar />
    <section className={styles.cart}>
      <h2 className={styles.title}>Ваша корзина</h2>

      {!cart.length ? (
        <div className={styles.empty}>Тут пока что пусто</div>
      ) : (
        <>
          <div className={styles.list}>
            {cart.map((item) => {
              const { title, category, image01, price, id, quantity } = item;

              return (
                <div className={styles.item} key={id}>
                  <div
                    className={styles.image}
                    style={{ backgroundImage: `url(${image01})` }}
                  />
                  <div className={styles.info}>
                    <h3 className={styles.name}>{title}</h3>
                    <div className={styles.category}>{category.name}</div>
                  </div>

                  <div className={styles.price}>{price}Р</div>

                  <div className={styles.quantity}>
                    <div
                      className={styles.minus}
                      onClick={() =>
                        changeQuantity(item, Math.max(1, quantity - 1))
                      }
                    >
                      <svg className="icon">
                        <use
                          xlinkHref={`${process.env.PUBLIC_URL}/sprite.svg#minus`}
                        />
                      </svg>
                    </div>

                    <span>{quantity}</span>

                    <div
                      className={styles.plus}
                      onClick={() =>
                        changeQuantity(item, Math.max(1, quantity + 1))
                      }
                    >
                      <svg className="icon">
                        <use
                          xlinkHref={`${process.env.PUBLIC_URL}/sprite.svg#plus`}
                        />
                      </svg>
                    </div>
                  </div>

                  <div className={styles.total}>{price * quantity}Р</div>

                  <div
                    className={styles.close}
                    onClick={() => removeItem(item.id)}
                  >
                    <svg className="icon">
                      <use
                        xlinkHref={`${process.env.PUBLIC_URL}/sprite.svg#close`}
                      />
                    </svg>
                  </div>
                </div>
              );
            })}
          </div>

          <div className={styles.actions}>
            <div className={styles.total}>
              Всего:{" "}
              <span>
                {sumBy(cart.map(({ quantity, price }) => quantity * price))}Р
              </span>
            </div>            
          </div>
        </>
      )}
    </section>


    {!cart.length ? (
        <div className={styles.empty}></div>
      ) : (
    <section className={styles.cart} style={{width: "100%"}}>
      <h2 className={styles.title} style={{marginBottom: "20px"}}>Оформление заказа</h2>    
        <>
        <form className={stylesProd.form} onSubmit={handleSubmit}>
          <div className={stylesProd.group}>
            <input
              type="text"
              placeholder="Телефон заказчика"
              name="consumerPhone"
              value={values.consumerPhone}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={stylesProd.group}>
            <input
              type="address"
              placeholder="Адрес доставки"
              name="consumerAddress"
              value={values.consumerAddress}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className={stylesProd.submit}>
            Заказать
          </button>
        </form>
        </>      
        </section>
      )}
    </>    
  );
};

export default Cart;
