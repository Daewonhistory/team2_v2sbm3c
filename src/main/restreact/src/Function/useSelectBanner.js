import React, { useState, useEffect } from 'react';

function useSelectBanner(maxHeight) {
    const [selectorOn, setSelectorOn] = useState(true)
    const [innerHeight, setInnerHeight] = useState('auto')

    useEffect(()=>{
        if(selectorOn){
            setInnerHeight(`${maxHeight}px`)
        }else{
            setInnerHeight(0)
        }
    },[selectorOn, maxHeight])

    return {
        selectorOn,
        setSelectorOn,
        innerHeight
    }
}

export default useSelectBanner;