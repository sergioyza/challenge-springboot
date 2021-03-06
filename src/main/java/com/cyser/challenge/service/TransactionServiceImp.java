package com.cyser.challenge.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cyser.challenge.dbconexion.TransactionDBConexion;
import com.cyser.challenge.dto.SumResponseDto;
import com.cyser.challenge.dto.TransactionDataRequestDto;
import com.cyser.challenge.dto.TransactionReportResponseDto;
import com.cyser.challenge.entity.TransactionDataEntity;
import com.cyser.challenge.util.TransactionUtil;
import com.cyser.challenge.util.WeeksUtil;

@Service
public class TransactionServiceImp implements TransactionInterface {

	@Autowired
	TransactionDBConexion transactionDBConexion;
	
	@Autowired
	TransactionUtil transactionUtil;
	
	@Override
	public ResponseEntity<?> addTransaction(TransactionDataRequestDto transactionDataRequestDto) {
		// TODO Auto-generated method stub
		return transactionDBConexion.addTransaction(transactionDataRequestDto);
	}

	@Override
	public ResponseEntity<?> showTransaction(long transaction_id, long user_id) {
		// TODO Auto-generated method stub
		return transactionDBConexion.showTransaction(transaction_id,user_id);
	}

	@Override
	public ResponseEntity<?> listTransaction(long user_id) {
		// TODO Auto-generated method stub
		return transactionDBConexion.listTransaction(user_id);
	}

	@Override
	public ResponseEntity<?> sumTransaction(long user_id) {
		
		SumResponseDto responseSumDto = new SumResponseDto();
		
		ArrayList<TransactionDataEntity> listTransactionDataEntity = (ArrayList<TransactionDataEntity>) transactionDBConexion.listTransaction(user_id).getBody();
		//get id User from list
		TransactionDataEntity transactionDataEntity =listTransactionDataEntity.stream().findFirst().get();
		//sum amount form user id
		float amountTotal= (float) listTransactionDataEntity.stream()
			      .mapToDouble(o -> o.getAmount())
			      .sum();
		
		responseSumDto.setUser_id(transactionDataEntity.getUserId());
		responseSumDto.setSuma(amountTotal);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseSumDto);
		
	}

	@Override
	public ResponseEntity<?> reportTransaction(long user_id) {
		
		ArrayList<TransactionDataEntity> listTransactionDataEntity = (ArrayList<TransactionDataEntity>) transactionDBConexion.listTransaction(user_id).getBody();
		ArrayList<TransactionReportResponseDto> listTransactionReportResponseDto = new ArrayList<TransactionReportResponseDto>();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(transactionUtil.historialInfo(10,listTransactionReportResponseDto,listTransactionDataEntity,0));
	}

	
}
