const axios = require("axios");
const md5 = require("js-md5");

// 请修改以下五行
const appKey = "";
const appId = "";
const appSecret = "";
const regId = ""; // 请确保该regId(我们俗称 Device Token)已经被添加进Vivo控制台测试设备
const targetConversationID = "";
const packageName = "";

const timestamp = new Date().getTime();
const originSign = appId + appKey + timestamp + appSecret;
const signMD5 = md5(originSign);

axios
  .post("https://api-push.vivo.com.cn/message/auth", {
    appId,
    appKey,
    timestamp,
    sign: signMD5,
  })
  .then((res) => {
    console.log(res);
    const authToken = res.data.authToken;
    sendTestMsg(authToken);
  })
  .catch((error) => {
    console.error(error);
  });

const sendTestMsg = (token) => {
  axios
    .post(
      "https://api-push.vivo.com.cn/message/send",
      {
        regId,
        notifyType: 3,
        skipType: 4,
        skipContent:
          `tencent_im_push://${packageName}/message?#Intent;scheme=tencent_im_push;launchFlags=0x4000000;end`,
        title: "测试推送",
        content: "推送详情测试",
        requestId: timestamp.toString(),
        pushMode: 1,
        clientCustomMap: {
          ext: {
            conversationID: targetConversationID,
          },
        },
      },
      {
        headers: {
          "Content-Type": "application/json",
          authToken: token,
        },
      }
    )
    .then((res) => {
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};
