<template>
  <!--    课程详情-->
  <div id="courseInfo">
    <!--    title  -->
    <DrawerTools :info="{user,course}"></DrawerTools>
    <div style="margin: 105px auto 0;width: 1224px;">
      <div :style="{'background':'url(' + course.address + ')'}"
           style="margin: 105px auto 0;
                        height: 200px;
                        border-radius: 8px 8px 0 0;
                        padding: 52px 0 0 40px;
                        box-sizing: border-box;">
        <div style="font-size: 36px;color: #fff;display: flex">
          {{ course.name }}
          <div v-if="identity === 1">
            <i @click="$store.commit('setShowCourseEdit',true)"
               style="display: inline-block;
                            width: 24px;
                            height: 24px;
                            background: url(https://www.ketangpai.com/Public/Home/img/edit-course.png) 0/24px no-repeat;
                            cursor: pointer;
                            margin: 0 0 0 13px;"></i>
          </div>
        </div>
        <span style="display: block;
                            margin-top: 9px;
                            font-size: 20px;
                            color: rgba(255,255,255,1);
                            line-height: 28px;
                            width: 600px;">{{ course.className }}</span>
        <div style="height: 35px;
                            color: white;
                            display:flex;
                            justify-content: space-between;
                            margin: 20px auto 0;
                            font-size: 12px;
                            font-weight: 500;
                            text-align: center;">
          <div v-if="identity === 1" style="display: flex">
            <span style="display:flex;
                                    align-items: center;
                                    margin-right: 14px;
                                    padding: 0 8px;
                                    height: 24px;
                                    line-height: 24px;
                                    background: rgba(255,255,255,.4);
                                    cursor: pointer;">
              <i style="font-size: 18px;margin-top:2px" class="el-icon-s-grid"></i>加课二维码
            </span>
            <el-dropdown @command="handleCommand" trigger="click">
              <div style="margin-right: 14px;
                                        padding: 0 8px;
                                        height: 24px;
                                        font-size: 12px;
                                        line-height: 24px;
                                        background: rgba(255,255,255,.4);
                                        color: #FFFFFF;
                                        text-align: center;
                                        cursor: pointer;">加课码:
                <span>{{ course.deleting === '1' ? "已停用" : course.courseKey }}</span>
                <i class="el-icon-arrow-down el-icon--right" style=""></i>
              </div>
              <el-dropdown-menu style="width: 120px;text-align: center">
                <div v-if="course.deleting === '1'">
                  <el-dropdown-item command="1">启用</el-dropdown-item>
                </div>
                <div v-else>
                  <el-dropdown-item command="1">停用</el-dropdown-item>
                  <el-dropdown-item command="2">重置</el-dropdown-item>
                </div>
              </el-dropdown-menu>
            </el-dropdown>
            <span class="jump" @click="jump(0)">
              <i class="el-icon-s-data">
              </i>成员{{ student.length + teacher.length }}
            </span>
            <span class="jump" @click="$router.push('/future')">数据分析</span>
            <span class="jump" @click="jump(2)" style="background: #32BAF0;">
              <i style="display: inline-block;
                                background: url('https://www.ketangpai.com/Public/Home/img/achievementicons.png') no-repeat 0/14px;
                                vertical-align: -2px;
                                width: 14px;
                                height: 14px;"></i>成绩管理
            </span>
          </div>
          <div v-else style="display: flex;margin-top: -3px">
            <span style="
                                    margin-right: 14px;
                                    padding: 0 8px;
                                    font-size: 14px;
                                    height: 24px;
                                    line-height: 24px;
                                    background: rgba(255,255,255,.4);
                                    text-align: center;">
              加课码：{{ course.courseKey }}
            </span>
            <span class="jump" @click="jump(0)">
              <i class="el-icon-s-data">
              </i>成员{{ student.length + teacher.length }}
            </span>
            <span class="jump" @click="jump(2)">成绩</span>
          </div>
          <div v-if="identity === 1 && view !== '2'" style="margin:-48px 30px;display: flex">
            <span style="width: 80px;
                                    margin:-1px 0 0 15px;
                                    padding: 0 10px;">
              <span style="font-size: 44px;
                                    font-weight: 500;    display: block;
                                    line-height: 48px;
                                    height: 50px;">0</span>互动个数</span>
            <span style="width: 80px;
                                    margin:-1px 0 0 15px;
                                    padding: 0 10px;">
              <span style="font-size: 44px;
                                    font-weight: 500;    display: block;
                                    line-height: 48px;
                                    height: 50px;">{{ homework.length }}</span>发布作业</span>
            <span style="width: 80px;
                                    margin:-1px 0 0 15px;
                                    padding: 0 10px;">
              <span style="font-size: 44px;
                                    font-weight: 500;    display: block;
                                    line-height: 48px;
                                    height: 50px;">0</span>发布测试</span>
          </div>
        </div>
        <div v-if="identity === 1" style="color: #FFF;
                        background: url(https://www.ketangpai.com/Public/Home/img/panneltools/theme.png) 0 no-repeat;
                        position: absolute;
                        padding-left: 30px;
                        line-height: 18px;
                        z-index: 4;
                        margin: -164px 0 0 1082px;
                        cursor: pointer;">
          更改背景
        </div>
      </div>
      <el-tabs v-model="view">
        <el-tab-pane name="0" label="课堂互动">
          <StayTuned></StayTuned>
        </el-tab-pane>
        <el-tab-pane name="1" label="作业" v-if="flag">
          <Homework :info="{user,homework,identity}"></Homework>
        </el-tab-pane>
        <el-tab-pane name="2" label="话题">
          <StayTuned></StayTuned>
        </el-tab-pane>
        <el-tab-pane name="3" label="资料">
          <StayTuned></StayTuned>
        </el-tab-pane>
        <el-tab-pane name="4" label="测试(考试)">
          <StayTuned></StayTuned>
        </el-tab-pane>
        <el-tab-pane name="5" label="公告" v-if="flag">
          <Inform :info="{user,identity,course}"></Inform>
        </el-tab-pane>
      </el-tabs>
    </div>

    <FloatTools></FloatTools>
    <CourseEdit :info="{master:user.id,course,new:'0'}" @getCourse="getCourse"></CourseEdit>
  </div>
</template>

<script>
import DrawerTools from "../components/tools/DrawerTools.vue";
import FloatTools from "../components/tools/FloatTools.vue";
import StayTuned from "../components/common/StayTuned.vue";
import CourseEdit from "../components/course/CourseEdit.vue";
import Homework from "../components/homework/Homework.vue";
import Inform from "../components/inform/Inform.vue";

export default {
  name: "CourseInfo",
  components: {Homework, StayTuned, FloatTools, DrawerTools, CourseEdit, Inform},
  data() {
    return {
      view: localStorage.getItem('courseView') ? localStorage.getItem('courseView') : '1',
      user: {},
      identity: '',
      course: {},
      teacher: [],
      student: [],
      homework: [],
      flag: false,
      // courseId:localStorage.getItem('courseId')
    }
  },
  mounted() {
    this.getUser()
  },
  watch: {
    view: function (view) {
      localStorage.setItem('courseView', view)
    },
  },
  methods: {
    jump(view) {
      localStorage.setItem('view', view);
      this.$router.push('/memberManage')
    },
    getUser() {
      this.$axios.post('start/user', {
        token: this.$store.state.Authorization
      }, {
        headers: {'Authorization': this.$store.state.Authorization}
      }).then(resp => {
        if (resp.status === 200) {
          this.user = resp.data.user
          this.user.address = resp.data.address
          this.identity = resp.data.user.account.startsWith('s') ? 0 : 1
          this.getCourse()
        }
      }).catch(resp => {
        console.log(resp)
      })
    },
    getCourse() {
      this.$axios.post('/start/courseInfo',
          {courseId: localStorage.getItem('courseId')},
          {headers: {'Authorization': this.$store.state.Authorization}}
      ).then(resp => {
        if (resp.status === 200) {
          this.course = resp.data.course
          this.course.address = resp.data.address
          if (this.identity === 1) {
            for (let i = 0; i < resp.data.tc.length; i++) {
              if (this.user.id === resp.data.tc[i].teacher) {
                if (resp.data.tc[i].role === '学生') {
                  this.identity = 0
                }
              }
            }
          }
          this.homework = resp.data.homework
          this.teacher = resp.data.teacher
          this.student = resp.data.student
          this.flag = true;
        }
      })

    },
    changeCourseKey() {
      this.$axios.post("/teacher/changeCourseKey",
          {id: this.course.id, deleting: this.course.deleting},
          {headers: {'Authorization': this.$store.state.Authorization}}
      ).then(resp => {
        this.$message({
          showClose: true,
          center: true,
          offset: this.$store.state.tip,
          message: resp.data
        })
        this.course.deleting = this.course.deleting === '0' ? '1' : '0'
      })
    },
    resetCourseKey() {
      this.$confirm('重置后原来的6位邀请码将失效', '要重置邀请码吗?', {
        showClose: false, lockScroll: false, closeOnClickModal: false
      }).then(() => {
        this.$axios.post("/teacher/resetCourseKey",
            {id: this.course.id},
            {headers: {'Authorization': this.$store.state.Authorization}}
        ).then(resp => {
          this.$message({
            showClose: true,
            center: true,
            offset: this.$store.state.tip,
            message: resp.data
          })
          // this.$router.go(0)
        })
      })
    },
    handleCommand(command) {
      command === '1' ? this.changeCourseKey() : this.resetCourseKey()
    }
  }
}
</script>

<style scoped>
#courseInfo .el-tabs__nav-scroll {
  padding-left: 35px;
  height: 72px;
  font-size: 16px;
  width: 1224px;
  color: #818181;
  background: rgba(241, 243, 244, 1);
  border-radius: 0 0 8px 8px;
  margin: 0 auto;
}

#courseInfo .el-tabs__header {
  padding: 0;
  position: relative;
  line-height: 0 !important;
  margin: 0 !important;
  border: 0 !important;
  outline: none !important;
}

#courseInfo .el-tabs__nav-wrap::after, .el-tabs__active-bar {
  height: 0 !important;
}

#courseInfo .el-tabs__item {
  min-width: 100px;
  height: 71px;
  padding: 0 10px;
  margin-left: 20px;
  font-size: 16px;
  font-weight: 400;
  line-height: 70px;
  position: relative;
  display: inline-block;
  text-align: center;
}

#courseInfo .el-tabs__item.is-active {
  border-left: 2px solid transparent;
  border-right: 2px solid transparent;
  font-size: 16px;
  font-weight: 500;
  color: rgba(59, 61, 69, 1);
  border-bottom: 4px solid #328eeb;
}

#courseInfo .jump {
  margin-right: 14px;
  padding: 0 8px;
  height: 24px;
  line-height: 24px;
  background: rgba(255, 255, 255, .4);
  cursor: pointer;
}

#courseInfo .jump:hover {
  text-decoration: underline;
}
</style>
