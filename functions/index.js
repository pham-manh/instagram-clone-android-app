"use strict";

// const { onRequest} = require("firebase-functions/v2/https");
// const logger = require("firebase-functions/logger");

const functions = require("firebase-functions");
const nodemailer = require("nodemailer");

const gmailEmail = functions.config().gmail.email;
const gmailPassword = functions.config().gmail.password;

const mailTransport = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: gmailEmail,
    pass: gmailPassword,
  },
});

const APP_NAME = "Instagram Clone";

exports.sendWelcomeEmail = functions.auth.user().onCreate((user) => {
  const email = user.email;
  const dispplayName = user.dispplayName;

  return sendWelcomeEmail(email, dispplayName);
});

async function sendWelcomeEmail(email, dispplayName) {
  const mailOptions = {
    from: `${APP_NAME}<noreply@firebaseConfig.com`,
    to: email,
  };

  mailOptions.subject = `Welcom to ${APP_NAME}`;
  mailOptions.text = `Hey ${
    dispplayName || ""
  }! Welcome to ${APP_NAME}, we hope you enjoy our service.`;

  try {
    await mailTransport.sendMail(mailOptions);
    console.log("New mail sent to: ", email);
    return null;
  } catch (error) {
    functions.logger.error("Error sending email:", error);
    throw error;
  }
}
