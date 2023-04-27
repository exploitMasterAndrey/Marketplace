import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { NavLink } from "react-router-dom";

import styles from "../../styles/Sidebar.module.css";

const ManagementSidebar = () => {
  const { currentUser } = useSelector(({ user }) => user);

  const list = [
    {id: 1, name: "Профиль"}
  ]

  // useEffect(() => {
  //   if (!currentUser) return;
  //   if(currentUser.roles.includes("SELLER")){
  //       const newList = list.push({id: 2, name:"Товары"})
  //       setList(newList);
  //   }
  //   if(currentUser.roles.includes("ADMIN")){
  //       const newList = list.push({id: 2, name:"Товары"}, {id: 3, name: "Продавцы"});
  //       setList(newList);
  //   }
  // }, [currentUser]);

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
          Help
        </a>
        <a
          href="/terms"
          target="_blank"
          className={styles.link}
          style={{ textDecoration: "underline" }}
        >
          Terms & Conditions
        </a>
      </div>
    </section>
  );
};

export default ManagementSidebar;
