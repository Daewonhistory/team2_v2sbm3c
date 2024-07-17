import React, { useState, useEffect } from 'react';
import { fetchCategories } from '../backend/categoryProc.js';
import './CategoryShortcut.css';

const CategoryShortcut = () => {
  const [categories, setCategories] = useState([]);

  const fetchData = async () => {
    try {
      const data = await fetchCategories();
      setCategories(data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 실행

  return (
      <section id="cate_shortcut_section">
        <h2>카테고리 숏컷</h2>
        <div className="category-list">
          {categories.map((category) => {
            const isLongText = category.name.length >= 5;
            const className = isLongText ? 'category-name long-text' : 'category-name';

            return (
                <div key={category.id} className="category-item" onclick={selectCategory(category.categoryno)}>
                  {category.image && (
                      <div className="category-image">
                        <img
                            src={`/category/storage/${category.image}`}
                            alt={`Category ${category.name}`}
                            style={{ width: '50px', height: '50px' }}
                        />
                      </div>
                  )}
                  <div className={className}>{category.name}</div>
                </div>
            );
          })}
        </div>
      </section>
  );
};

export default CategoryShortcut;
