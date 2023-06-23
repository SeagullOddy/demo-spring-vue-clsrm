<template>
  <div style="width: 160px;
                margin-right: 0;
                margin-top: 8px;
                display:flex;
                justify-content: space-between;
                align-items: center;
                text-align: center;
                color:black;">
    <div style="display: flex;align-items: center;cursor: pointer">
      <img src="https://www.ketangpai.com/Public/Home/img/tool_icon.png" alt="" style="width: 25px;height: 25px;"/>
      <span style="margin-left:5px;">工具</span>
    </div>
    <el-badge :value="5">
      <svg style="margin:4px 0 0 -5px" t="1592388735879" class="icon" viewBox="0 0 1024 1024" version="1.1"
           xmlns="http://www.w3.org/2000/svg" p-id="1368" width="19" height="19">
        <path
            d="M851.2 742.4V454.4c0-166.4-121.6-307.2-281.6-332.8v-64C569.6 25.6 544 0 512 0s-57.6 25.6-57.6 57.6v64c-160 25.6-281.6 166.4-281.6 332.8v281.6c-32 0-57.6 25.6-57.6 57.6s25.6 57.6 57.6 57.6h684.8c32 0 57.6-25.6 57.6-57.6-6.4-25.6-32-51.2-64-51.2z m-108.8 0H281.6V454.4c0-128 102.4-230.4 230.4-230.4s230.4 102.4 230.4 230.4v288z m-172.8 166.4H454.4c-32 0-57.6 25.6-57.6 57.6s25.6 57.6 57.6 57.6h115.2c32 0 57.6-25.6 57.6-57.6s-25.6-57.6-57.6-57.6z"
            p-id="1369"></path>
      </svg>
    </el-badge>
    <el-tooltip :content="info.user.name" effect="light" placement="bottom">
      <el-dropdown @command="handleCommand" trigger="click">
        <div class="img fl">
          <img v-bind:src="info.user.address">
          <span></span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="4">
            <!--                            <i style="background-position: 5px -240px;"></i>-->
            开通VIP
          </el-dropdown-item>
          <el-dropdown-item command="2">
            <!--                            <i style="background-position: 5px -240px;"></i>-->
            机构账号绑定
          </el-dropdown-item>
          <el-dropdown-item command="3">
            <!--                            <i style="background-position: 5px -240px;"></i>-->
            个人设置
          </el-dropdown-item>
          <el-dropdown-item command="1">
            <!--                            <i style="background-position: 5px -240px;"></i>-->
            退出账户
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-tooltip>
  </div>
</template>

<script>
export default {
  name: "AccountTools",
  props: ['info'],
  data() {
    return {}
  },
  methods: {
    handleCommand(command) {
      if (command === "1") {
        this.$router.push('/login')
      } else {
        alert("敬请期待")
      }
    },
  }
}
</script>

<style scoped>
.img {
  width: 37px;
  height: 37px;
  position: relative;
  float: left;
  margin-left: 10px;
}

.fl {
  float: left;
}

.img img {
  width: 37px;
  height: 37px;
  border-radius: 37px;
  background-repeat: no-repeat;
  /* background-position: center; */
}
</style>