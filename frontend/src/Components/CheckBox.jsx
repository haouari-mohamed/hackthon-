import React from 'react';
import styled from 'styled-components';

const CheckBox = ({ togglePwdVisibility }) => {

  return (
    // <StyledWrapper>
    // 	<label className="container mt-2">
    // 		<div className='flex' >
    // 			<input defaultChecked="checked" type="checkbox" id='myCheckbox'  onClick={togglePwdVisibility} />
    // 			<div className="checkmark" />
    // 			<label htmlFor="myCheckbox" className='ml-2 cursor-pointer'>Show password</label>
    // 		</div>
    // 	</label>
    // </StyledWrapper>
    <StyledWrapper>
      <div className="flex items-center mt-3">
        <label className="checkBox block">
          <input id="ch1" type="checkbox" onClick={togglePwdVisibility} />
          <div className="transition" />
        </label>
        <label htmlFor="ch1" className='ml-2 cursor-pointer block font-light'>Show password</label>
      </div>
    </StyledWrapper>
  );
}

const StyledWrapper = styled.div`
  .clear {
    clear: both;
  }

  .checkBox {
    display: block;
    cursor: pointer;
    width: 12px;
    height: 12px;
    border: 3px solid rgba(255, 255, 255, 0);
    border-radius: 2px;
    position: relative;
    overflow: hidden;
    box-shadow: 0px 0px 0px 2px #fff;
  }

  .checkBox div {
    width: 40px;
    height: 40px;
    background-color: #fff;
    top: -52px;
    left: -52px;
    position: absolute;
    transform: rotateZ(45deg);
    z-index: 100;
  }

  .checkBox input[type=checkbox]:checked + div {
    left: -10px;
    top: -10px;
  }

  .checkBox input[type=checkbox] {
    position: absolute;
    left: 50px;
    visibility: hidden;
  }

  .transition {
    transition: 300ms ease;
  }`;

export default CheckBox;
