<template>
  <!--  课程管理  -->
  <div style="padding: 85px 4% 0 4%;">
    <!--  功能区-->
    <div style="
                    padding: 18px 0 10px 0;
                    display:flex;
                    font-size: 14px;
                    justify-content: space-between;
                    align-items: center;">
      <span v-if="topCourse.length === 0">全部课程</span>
      <span v-else>置顶课程</span>
      <div style="
                            display:flex;
                            justify-content: flex-end;
                            align-items: center;">
        <el-button @click="choose = 'courseSort';
           $store.commit('setShowCourseHandle',true)" icon="el-icon-office-building"
                   style="color: #78787a;" type="text"
                   v-if="otherCourse.length !== 0">课程排序
        </el-button>
        <el-button @click="choose = 'coursePigeonhole';
           $store.commit('setShowCourseHandle',true)" icon="el-icon-bank-card"
                   style="color: #78787a;margin-left: 28px;"
                   type="text">归档管理
        </el-button>
        <div v-if="identity === 1">
          <el-dropdown @command="handleCommand" trigger="click">
            <el-button style="margin-left: 26px;height: 36px;padding: 5px 15px" type="primary"> +
              创建/加入课程
            </el-button>
            <el-dropdown-menu slot="dropdown" style="width: 130px;">
              <!-- 1为老师，2为学生-->
              <el-dropdown-item command="1">创建课程</el-dropdown-item>
              <el-dropdown-item command="2">加入课程</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div v-else>
          <el-button @click="$store.commit('setShowCourseJoin',true)"
                     style="margin-left: 20px;height: 36px;padding: 5px 15px"
                     type="primary"> + 加入课程
          </el-button>
        </div>
      </div>
    </div>
    <!--        课程区-->
    <div>
      <div v-if="topCourse.length !== 0">
        <div style="
                    padding: 20px 0;
                    border-top: 1px solid rgb(226,230,237);
                    display:flex;
                    flex-wrap: wrap;
                    align-items: center">
          <!--          循环渲染-->
          <CourseCard v-for="(item,i) in topCourse" :key="i" :info="{user,course:item}"
                      @getCourse="getCourse" @showCourseOut="showCourseOut"
                      @showCourseEdit="showCourseEdit"
                      @showCoursePigeonhole="showCoursePigeonhole"
                      @showCourseSend="showCourseSend"></CourseCard>
        </div>
        <!--        下一板块的-->
        <div style="margin:8px 0 20px 0">其它课程</div>
      </div>
      <div class="class-box" style="
                    padding: 20px 0;
                    border-top: 1px solid rgb(226,230,237);
                    display:flex;
                    flex-wrap: wrap;
                    align-items: center">
        <CourseCard v-for="(item,i) in otherCourse" :key="i" :info="{user,course:item}"
                    @getCourse="getCourse" @showCourseOut="showCourseOut"
                    @showCourseEdit="showCourseEdit"
                    @showCoursePigeonhole="showCoursePigeonhole"
                    @showCourseSend="showCourseSend"></CourseCard>
        <div style="width:255px;height: 234px;border-radius: 4px 4px 0 0;">
          <el-card :body-style="{width:'100%',height:'100%',padding:'0'}"
                   style="width: 100%;height: 100%;">
            <div
                style="padding: 18px 0 14px 0;height: 95px;background: url('https://www.ketangpai.com/Public/Home/img/create-course.png') no-repeat center;">
            </div>
            <div
                @click="identity === 0 ? $store.commit('setShowCourseJoin',true) : showCourseEdit({})"
                style="height: 145px;display: flex;justify-content: center;align-items: center;
                  text-align: center;line-height: 22px;cursor: pointer">
            <span>
                <!--  根据身份判断是创建还是加入课程-->
                <i class="el-icon-plus"></i><br/>{{ identity === 0 ? "加入课程" : "创建课程" }}
              </span>
            </div>
          </el-card>
        </div>
      </div>
    </div>
    <!--        对话区-->
    <div>
      <FloatTools></FloatTools>
      <CourseEdit :info="{master:user.id,course,new:this.new,identity}"
                  @getCourse="getCourse"></CourseEdit>
      <CourseJoin :info="{user,identity}" @getCourse="getCourse"></CourseJoin>
      <CourseHandle :info="{user,otherCourse,outCourse,choose}"
                    @getCourse="getCourse" @showCourseOut="showCourseOut"
                    @showCoursePigeonhole="showCoursePigeonhole"></CourseHandle>
      <CourseOut :info="{user,course,identity}" @getCourse="getCourse"></CourseOut>
      <CoursePigeonhole :info="{user,course,identity}" @getCourse="getCourse"></CoursePigeonhole>
      <CourseSent :info="{user,course}" @getCourse="getCourse"></CourseSent>
    </div>
  </div>
</template>

<script>
import CourseCard from "./CourseCard.vue";
import CourseJoin from "./CourseJoin.vue";
import CourseHandle from "./CourseHandle.vue";
import CourseEdit from "./CourseEdit.vue";
import CourseOut from "./CourseOut.vue";
import CoursePigeonhole from "./CoursePigeonhole.vue";
import FloatTools from "../tools/FloatTools.vue";
import CourseSent from "./CourseSend.vue";

export default {
  name: "CourseManage",
  components: {
    CourseSent,
    FloatTools,
    CourseOut, CourseEdit, CourseCard, CourseJoin, CourseHandle, CoursePigeonhole
  },
  data() {
    return {
      topCourse: [],
      otherCourse: [],
      outCourse: [],
      now: '',
      user: {},
      identity: '',
      choose: '',
      course: {},
      new: '1'
    }
  },
  mounted() {
    this.getUser()
  },
  methods: {
    getIdentity(role) {
      if (role === '管理员') {
        return '1'
      } else if (role === '助教') {
        return '2'
      } else {
        return '0'
      }
    },
    getUser() {
      this.$axios.post('start/user', {
        token: this.$store.state.Authorization
      }, {
        headers: {'Authorization': this.$store.state.Authorization}
      }).then(resp => {
        if (resp.status === 200) {
          this.user = resp.data.user
          this.identity = resp.data.user.account.startsWith('s') ? 0 : 1
          this.getCourse()
        }
      }).catch(resp => {
        console.log(resp)
      })
    },
    getCourse() {
      let api;
      if (this.identity === 0) {
        api = '/student'
      } else if (this.identity === 1) {
        api = '/teacher'
      } else {
        return
      }
      this.$axios.post(api + '/course',
          {token: this.$store.state.Authorization},
          {
            headers: {'Authorization': this.$store.state.Authorization}
          }).then(resp => {
        if (resp.status === 200) {
          this.topCourse = []
          this.otherCourse = []
          this.outCourse = []
          for (let i = 0; i < resp.data.length; i++) {
            resp.data[i].identity = this.getIdentity(resp.data[i].role)
            if (resp.data[i].pigeonhole === '0') {
              if (resp.data[i].top === '1') {
                this.topCourse.push(resp.data[i])
              } else {
                this.otherCourse.push(resp.data[i])
              }
            } else {
              this.outCourse.push(resp.data[i])
            }
          }
        }
      })
    },
    showCourseOut(course) {
      this.course = course
      this.$store.commit('setShowCourseOut', true)
    },
    showCourseEdit(course) {
      this.course = course
      this.new = course.id ? "0" : "1"
      this.$store.commit('setShowCourseEdit', true)
    },
    showCoursePigeonhole(course) {
      this.course = course
      this.$store.commit('setShowCoursePigeonhole', true)
    },
    showCourseSend(course) {
      this.course = course
      this.$store.commit('setShowCourseSend', true)
    },
    handleCommand(command) {
      if (command === '1') {
        this.showCourseEdit({})
      } else {
        this.$store.commit("setShowCourseJoin", true)
      }
    },
  },
}
</script>

<style scoped>
.class-box > div {
  margin: 11px;
}
</style>

