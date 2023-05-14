import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import { updateUser } from "../../features/user/userSlice";
import ManagementSidebar from "../Sidebar/ManagementSideBar";
import stylesProd from "../../styles/Cart.module.css";
import { createOrUpdateCategory } from "../../features/categories/categoriesSlice";

import styles from "../../styles/Profile.module.css";

const CategorytM = () => {
  const dispatch = useDispatch();
  const { currentUser } = useSelector(({ user }) => user);
  const { list } = useSelector(({ categories }) => categories);

  const initialValues = {
    id: "",
    name: "",
    image: ""
  }

  const [values, setValues] = useState(initialValues);

  const handleChange = ({ target: { value, name } }) => {
    setValues({ ...values, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(createOrUpdateCategory(values));
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
              placeholder="Id категории"
              name="id"
              value={values.id}
              autoComplete="off"
              onChange={handleChange}
            />
          </div>

          <div className={styles.group}>
            <input
              type="name"
              placeholder="Название"
              name="name"
              value={values.name}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="text"
              placeholder="Картинка"
              name="image"
              value={values.image}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>         

          <button type="submit" className={styles.submit}>
            Сохранить/Обновить
          </button>
        </form>
      )}
    </section>
    <section className={stylesProd.cart} style={{width: "100%"}}>
      <h2 className={stylesProd.title}>Категории</h2>

      {!list.length ? (
        <div className={stylesProd.empty}>Здесь пока что пусто</div>
      ) : (
        <>
          <div className={stylesProd.list}>
            {list.map((item) => {
              const { id, name, image } = item;

              return (
                <div className={styles.item} key={id} onClick={() => {
                        setValues({
                        id: id,
                        name: name,
                        image: image
                    })
                }}>
                  <div
                    className={stylesProd.image}
                    style={{ backgroundImage: `url(${image})` }}
                  />
                  <div className={stylesProd.info}>
                    <h3 className={stylesProd.name}>{name}</h3>
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

export default CategorytM;
