import React from "react";
import './Header.css';  // Import the CSS file

const Header = () => (
    <header className="header">
      <h1 className="header-title">
        EAT DAYS
      </h1>
      <a href="/chatbot">
        <img
            src="/images/icon/chatbot.png"
            className="chatbot-icon"
            alt="Chatbot"
        />
      </a>
    </header>
);

export default Header;
