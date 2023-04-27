import React from "react";

import styles from "../../styles/Home.module.css";

import BG from "../../images/computer.png";

const Poster = () => (
  <section className={styles.home}>
    <div className={styles.title}>Деньги Это Bless</div>
    <div className={styles.product}>
      <div className={styles.text}>
        <div className={styles.subtitle}>Лучший маркетплейс для вашего бизнеса</div>
        <h1 className={styles.head}>СТАНЬ ЧАСТЬЮ НАШЕЙ КОМАНДЫ УЖЕ СЕГОДНЯ</h1>
      </div>
      <div className={styles.image}>
        <img src={BG} alt="" />
      </div>
    </div>
  </section>
);

export default Poster;
