<template>
  <NavBar></NavBar>
  <router-view> </router-view>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
//import $ from 'jquery'



export default {
  components: {
    NavBar
  },
  setup() {
    // $.ajax({
    //   url:"http://127.0.0.1:3000/user/account/register/",
    //   type: "post",
    //   data:{
    //     username: 'dd',
    //     password: 'pdd',
    //     Confpassword: 'pdd',
    //   },
    //   success(reps) {
    //     console.log(reps);
    //   },
    //   error(reps) {
    //     console.log(reps);
    //   }
    // });
  }
}
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>
