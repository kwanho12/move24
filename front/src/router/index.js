import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: HomeView,
      alias: '/',
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/members/new',
      name: 'signup',
      component: () => import('../views/SignupView.vue')
    },
    {
      path: '/drivers-new',
      name: 'drivers-new',
      component: () => import("../views/PostDriverView.vue")
    },
    {
      path: '/drivers/:driverId',
      name: 'driver',
      component: () => import("../views/DriverView.vue")
    },
    {
      path: '/drivers',
      name: 'drivers',
      component: () => import("../views/DriversView.vue")
    },
    {
      path: '/review',
      name: 'review',
      component: () => import('../views/ReviewView.vue')
    },
  ]
});

export default router;
