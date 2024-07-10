import React from "react";

const LocationShortcut = () => (
    <section id="loca_shortcut_section">
      <h2>위치기반 숏컷</h2>
      <div id="midarea_div">
        <button className="button" type="button">
          서울시 강남구
        </button>
        {/* Additional buttons can be added similarly */}
      </div>
      <div id="botarea_div" style={{ display: "none", marginTop: "30px" }}></div>
    </section>
);

export default LocationShortcut;
