import React from 'react';
import './Footer.css'
import { FaInstagram } from "react-icons/fa";
import { FaXTwitter } from "react-icons/fa6";
import { CiYoutube } from "react-icons/ci";


export default function Footer() {
    return (
        <div id='footer'>
            <div className="footer-title">
                <h1>©Restaurant Woody</h1>
            </div>
            <div className="footer-social">
                <FaInstagram className='icon'/>
                <CiYoutube className='icon'/>
            </div>
        </div>
    );
}

