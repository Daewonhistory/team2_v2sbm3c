import React from "react";

const Header = () => (
    <header>
      <h1
          style={{
            margin: 0,
            textAlign: "center",
            flexGrow: 1,
            backgroundColor: "#4CAF50",
            padding: "10px",
            color: "#fff",
          }}
      >
        EAT DAYS
      </h1>
      <a href="/chatbot">
        <img
            src="/images/icon/chatbot.png"
            style={{
              width: "50px",
              position: "absolute",
              right: "10px",
              top: "50%",
              transform: "translateY(-50%)",
            }}
            alt="Chatbot"
        />
      </a>
    </header>
);

export default Header;
