import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import ManagementSidebar from "../Sidebar/ManagementSideBar";
import stylesProd from "../../styles/Cart.module.css";
import { createOrUpdateProduct, deleteProduct } from "../../features/products/productsSlice";

import styles from "../../styles/Profile.module.css";

const ProductM = () => {
  const dispatch = useDispatch();
  const { currentUser } = useSelector(({ user }) => user);
  const[list, setList] = useState(useSelector(({ products }) => products.list));


  useEffect(() => {
    if(currentUser.roles.includes("SELLER")){
      let filtered = list.filter((item) => item.seller.id == currentUser.id);
      setList(filtered)
    }

  }, []);

  const initialValues = {
    id: "",
    title: "",
    description: "",
    image01: "",
    image02: "",
    image03: "",
    price: "",
    category: "",
    sellerId: currentUser.id
  }

  const [values, setValues] = useState(initialValues);

  const handleChange = ({ target: { value, name } }) => {
    setValues({ ...values, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(createOrUpdateProduct(values));
    setValues(initialValues);
  }; 

  return (
    <>
    <ManagementSidebar />   
    <section className={styles.profile}>
      {!currentUser ? (
        <span>Войдите в аккаунт</span>
      ) : (
        <form className={styles.form} onSubmit={handleSubmit}>
          <div className={styles.group}>
            <input
              type="id"
              placeholder="Id продукта"
              name="id"
              value={values.id}
              autoComplete="off"
              onChange={handleChange}
            />
          </div>

          <div className={styles.group}>
            <input
              type="title"
              placeholder="Название"
              name="title"
              value={values.title}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="description"
              placeholder="Описание"
              name="description"
              value={values.description}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="image01"
              placeholder="Картинка 1"
              name="image01"
              value={values.image01}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="image02"
              placeholder="Картинка 2"
              name="image02"
              value={values.image02}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="image03"
              placeholder="Картинка 3"
              name="image03"
              value={values.image03}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="price"
              placeholder="Цена"
              name="price"
              value={values.price}
              autoComplete="off"
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.group}>
            <input
              type="category"
              placeholder="Категория"
              name="category"
              value={values.category}
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
      <h2 className={stylesProd.title}>Товары</h2>

      {!list.length ? (
        <div className={stylesProd.empty}>Here is empty</div>
      ) : (
        <>
          <div className={stylesProd.list}>
            {list.map((item) => {
              const { title, category, image01, image02, image03, description, price, id } = item;

              return (
                <div className={styles.item} key={id} onClick={() => {
                        setValues({
                        id: id,
                        title: title,
                        description: description,
                        image01: image01,
                        image02: image02,
                        image03: image03,
                        price: price,
                        title: title,
                        category: category.name
                    })
                }}>
                  <div
                    className={stylesProd.image}
                    style={{ backgroundImage: `url(${image01})` }}
                  />
                  <div className={stylesProd.info}>
                    <h3 className={stylesProd.name}>{title}</h3>
                    <div className={stylesProd.category}>{category.name}</div>
                  </div>

                  <div className={stylesProd.price}>{price}$</div>

                  <div
                    className={stylesProd.close}
                    onClick={()=> {dispatch(deleteProduct())}}
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

export default ProductM;
