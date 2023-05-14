import React, { useEffect, useState } from "react";
import Sidebar from "../Sidebar/Sidebar";
import { getAllOrders } from "../../features/user/userSlice";
import { useSelector } from "react-redux";

import styles from "../../styles/Cart.module.css";
import stylesProd from "../../styles/Profile.module.css";
import { sumBy } from "../../utils/common";
import ManagementSidebar from "../Sidebar/ManagementSideBar";

const Orders = () => {
    const [orders, setOrders] = useState([]);
    const { currentUser } = useSelector(({ user }) => user);
    useEffect(() => {
        getAllOrders(currentUser.id).then((data) => {
            setOrders(data);
        })
    }, []);

    return (
        <>
            <ManagementSidebar />
            <section className={styles.cart}>
                <h2 className={styles.title} style={{paddingBottom: "10px"}}>Ваши заказы</h2>

                {!orders.length ? (
                    <div className={styles.empty}>Тут пока что пусто</div>
                ) : (
                    <>
                        {orders.map((order) => {
                            const { products, id } = order;

                            return (
                                <div style={{borderTop: '1px solid var(--bg)', borderBottom: '1px solid var(--bg)', padding: "10px"}}>
                                    <h3 style={{color: "var(--violet)"}}>№{id}</h3>
                                    <div className={styles.list}>
                                        {products.map((item) => {
                                            const { title, category, image01, price, id } = item;

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
                                                </div>
                                            );
                                        })}
                                    </div>
                                </div>
                            );
                        })}


                    </>
                )}
            </section>
        </>
    );
};

export default Orders;
