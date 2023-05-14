import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { NavLink } from "react-router-dom";

import styles from "../../styles/Sidebar.module.css";

const ManagementSidebar = () => {
  const { currentUser } = useSelector(({ user }) => user);

  const [list, setList] = useState([
    {id: 1, name: "Профиль"},
    {id: 5, name: "Мои заказы"}
  ]);

  const adminList = [
    {id: 1, name: "Профиль"},
    {id: 2, name: "Товары"},
    {id: 3, name: "Продавцы"},
    {id: 4, name: "Категории"},
    {id: 5, name: "Мои заказы"}
  ]

  const sellerList = [
    {id: 1, name: "Профиль"},
    {id: 2, name: "Товары"},
    {id: 5, name: "Мои заказы"}
  ]

  useEffect(() => {
    if (!currentUser) return;
    if(currentUser.roles.includes('SELLER')){
        setList(sellerList);
    }
    if(currentUser.roles.includes('ADMIN')){
        setList(adminList);
    }
  }, []);

  return (
    <section className={styles.sidebar}>        
      <div className={styles.title}>УПРАВЛЕНИЕ</div>
      <nav>
        <ul className={styles.menu}>
          {list.map(({ id, name }) => (
            <li key={id}>
              <NavLink
                className={({ isActive }) =>
                  `${styles.link} ${isActive ? styles.active : ""}`
                }
                to={`/management/${id}`}
              >
                {name}
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>

      <div className={styles.footer}>
        <a href="/help" target="_blank" className={styles.link}>
          Помощь
        </a>
        <a
          href="/terms"
          target="_blank"
          className={styles.link}
          style={{ textDecoration: "underline" }}
        >
          Условия
        </a>
      </div>
    </section>
  );
};

export default ManagementSidebar;
