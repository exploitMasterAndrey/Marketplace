import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { ROUTES } from "../../utils/routes";

import styles from "../../styles/Product.module.css";

import { addItemToCart } from "../../features/user/userSlice";

const Product = (item) => {
  const { title, price, image01, image02, image03, description } = item;
  const images = [image01, image02, image03];

  const dispatch = useDispatch();

  const [currentImage, setCurrentImage] = useState();
  const { currentUser } = useSelector(({ user }) => user);

  useEffect(() => {
    if (!images.length) return;

    setCurrentImage(images[0]);
  }, []);

  const addToCart = () => {
    dispatch(addItemToCart(item));
  };

  return (
    <section className={styles.product}>
      <div className={styles.images}>
        <div
          className={styles.current}
          style={{ backgroundImage: `url(${currentImage})` }}
        />
        <div className={styles["images-list"]}>
          {images.map((image, i) => (
            <div
              key={i}
              className={styles.image}
              style={{ backgroundImage: `url(${image})` }}
              onClick={() => setCurrentImage(image)}
            />
          ))}
        </div>
      </div>
      <div className={styles.info}>
        <h1 className={styles.title}>{title}</h1>
        <div className={styles.price}>{price}Р</div>
        
        <p className={styles.description}>{description}</p>

        <div className={styles.actions}>
          <button
            onClick={addToCart}
            className={styles.add}
            disabled={!currentUser}
          >
            Add to cart
          </button>
          {/* <button className={styles.favourite}>Add to favourites</button> */}
        </div>

        <div className={styles.bottom}>
          <div className={styles.purchase}>19 раз заказали</div>

          <Link to={ROUTES.HOME}>На главную</Link>
        </div>
      </div>
    </section>
  );
};

export default Product;
