import cx_Oracle
import pandas as pd
from flask import Flask, render_template, request, jsonify
from flask_cors import CORS, cross_origin

app = Flask(__name__)  # __name__ == '__main__'
CORS(app)

def get_weekday_summary():
    dsn_tns = cx_Oracle.makedsn('3.39.57.96', '1521', service_name='XE')
    conn = cx_Oracle.connect(user='team2', password='69017000', dsn=dsn_tns)
    
    cursor = conn.cursor()
    cursor.execute("SELECT reserve_date, curr_person FROM admitperson where restno = 3846")
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    columns = ['Date', 'People']
    df = pd.DataFrame(data, columns=columns)
    df['Date'] = pd.to_datetime(df['Date'], format='%y/%m/%d')

    day_name_map = {
        'Monday': '월요일',
        'Tuesday': '화요일',
        'Wednesday': '수요일',
        'Thursday': '목요일',
        'Friday': '금요일',
        'Saturday': '토요일',
        'Sunday': '일요일'
    }
    df['Weekday'] = df['Date'].dt.day_name().map(day_name_map)
    df['People'] = df['People'].astype(int)

    weekday_summary = df.groupby('Weekday')['People'].sum().reset_index()
    weekday_order = ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일']
    weekday_summary['Weekday'] = pd.Categorical(weekday_summary['Weekday'], categories=weekday_order, ordered=True)
    weekday_summary = weekday_summary.sort_values('Weekday')

    return weekday_summary

@app.route('/owner')

def index():
    return render_template('layout.html')

@app.route('/generate_chart', methods=['POST'])

def generate_chart():
    analysis_type = request.form['analysis_type']

    if analysis_type == "요일별 분석":
        summary = get_weekday_summary()
        chart_data = summary.values.tolist()
        title = "요일별 예약 수"
        legend = "요일별 예약 수 합계"

        return jsonify(chart_data=chart_data, title=title, legend=legend)
    else:
        return jsonify({"error": "시간 분석은 아직 구현되지 않았습니다."})


app.run(host='0.0.0.0')