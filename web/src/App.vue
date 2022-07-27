<template>
  <NavBar></NavBar>
  <router-view> </router-view>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import $ from 'jquery'



export default {
  components: {
    NavBar
  },
  setup() {
    $.ajax({
      url:"http://127.0.0.1:3000/user/account/token/",
      type: "post",
      data:{
        username: 'yxr',
        password: 'pyxr',
      },
      success(reps) {
        console.log(reps);
      },
      error(reps) {
        console.log(reps);
      }
    });

    $.ajax({
      url:"http://127.0.0.1:3000/user/account/info/",
      type: "get",
      headers: {
        Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmMmJjMjJiMjA4ZmY0MTI5YWU3Y2U2N2I0MDAwZmU4MiIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY1ODkwNjk5NSwiZXhwIjoxNjYwMTE2NTk1fQ.m8lX6SqrmY0OJoeXIuzRbs7Nx257_D1_5-kNoOVsjFM",
      },
      success(reps) {
        console.log(reps);
      },
      error(reps) {
        console.log(reps);
      }
    });

    $.ajax({
      url:"http://127.0.0.1:3000/user/account/register/",
      type: "post",
      data:{
        username: 'dd',
        password: 'pdd',
        Confpassword: 'pdd',
      },
      success(reps) {
        console.log(reps);
      },
      error(reps) {
        console.log(reps);
      }
    });
  }
}
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>
