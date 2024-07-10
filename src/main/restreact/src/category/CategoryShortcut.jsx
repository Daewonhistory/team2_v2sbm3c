// frontend/src/components/CategoryShortcut.js

import React, { useState, useEffect } from 'react';
import { fetchCategories } from '../backend/categoryProc.js';
import './CategoryShortcut.css'; // CSS 파일 임포트

const CategoryShortcut = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchCategories();
        setCategories(data); // 카테고리 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };
    fetchData();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 실행

  return (
      <section id="cate_shortcut_section">
        <h2>카테고리 숏컷</h2>
        <div className="category-list">
          {categories.map((category, index) => (
              <div key={category.id} className="category-item">
                {category.image && (
                    <div className="category-image">
                      <img
                          src={`/category/storage/${category.image}`}
                          alt={`Category ${category.name}`}
                          style={{ width: '50px', height: '50px' }}
                      />
                    </div>
                )}
                <div className="category-name">{category.name}</div>
              </div>
          ))}
        </div>
      </section>
  );
};

export default CategoryShortcut;
