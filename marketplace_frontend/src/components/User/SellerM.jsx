import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import ManagementSidebar from "../Sidebar/ManagementSideBar";
import stylesProd from "../../styles/Cart.module.css";
import userStyles from "../../styles/User.module.css"
import styles from "../../styles/Profile.module.css";
import { createOrUpdateSeller, deleteSeller } from "../../features/sellers/sellerSlice";

const SellerM = () => {
  const dispatch = useDispatch();
  const { currentUser } = useSelector(({ user }) => user);
  const list = useSelector(({ sellers }) => sellers.list);

  const initialValues = {
    id: "",
    email: "",
    username: "",
    password: "",
    avatar: ""
  }

  const [values, setValues] = useState(initialValues);

  const handleChange = ({ target: { value, name } }) => {
    setValues({ ...values, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(createOrUpdateSeller(values));
    setValues(initialValues);
  }; 

  return (
    <>
    <ManagementSidebar />   
    <section className={styles.profile}>
      {!currentUser ? (
        <span>You need to log in</span>
      ) : (
        <form className={styles.form} onSubmit={handleSubmit}>
          <div className={styles.group}>
            <input
              type="id"
              placeholder="Id продавца"
              name="id"
              value={values.id}
              autoComplete="off"
              onChange={handleChange}
            />
          </div>

          <div className={styles.group}>
            <input
              type="email"
              placeholder="Почта"
              name="email"
              value={values.email}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="username"
              placeholder="Имя"
              name="username"
              value={values.username}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="password"
              placeholder="Пароль"
              name="password"
              value={values.password}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="avatar"
              placeholder="Аватар"
              name="avatar"
              value={values.avatar}
              autoComplete="off"
              onChange={handleChange}
            />
          </div>          

          <button type="submit" className={styles.submit}>
            Сохранить/Обновить
          </button>
        </form>
      )}
    </section>
    <section className={stylesProd.cart} style={{width: "100%"}}>
      <h2 className={stylesProd.title}>Продавцы</h2>

      {!list.length ? (
        <div className={stylesProd.empty}>Продавцов не добавлено</div>
      ) : (
        <>
          <div className={stylesProd.list}>
            {list.map((item) => {
              const { id, email, username, avatar } = item;

              return (
                <div className={userStyles.item} key={id} onClick={() => {
                        setValues({
                            id: id,
                            email: email,
                            username: username,
                            avatar: avatar
                    })
                }}>
                  <div
                    className={stylesProd.image}
                    style={{ backgroundImage: `url(${avatar})` }}
                  />
                  <div className={stylesProd.info}>
                    <h3 className={stylesProd.name}>{username}</h3>
                  </div>

                  <div
                    className={stylesProd.close}
                    onClick={()=> {dispatch(deleteSeller(id))}}
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
        </>
      )}
    </section>
    </>
  );
};

export default SellerM;
