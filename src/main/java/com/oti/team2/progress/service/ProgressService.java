package com.oti.team2.progress.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oti.team2.progress.dao.IProgressDao;
import com.oti.team2.progress.dto.Progress;
import com.oti.team2.srdemand.service.ISrDemandService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProgressService implements IProgressService {

	@Autowired
	IProgressDao progressDao;
	
	@Autowired
	ISrDemandService srDemandService;

	/**
	 * 
	 * @author 여수한 작성일자 : 2023-02-23
	 * @return sr요청 진척률 조회
	 */
	@Override
	public List<Progress> getProgress(String srNo) {
		return progressDao.selectProgressByDmndNo(srNo);
	}

	/**
	 * 
	 * @author 여수한 작성일자 : 2023-02-27
	 * @return sr요청 진척률 수정
	 */
	@Transactional
	public void updateProgress(int prgrsRt, String bgngYmd, String endYmd, int prgrsId, String srNo) {
		if (prgrsRt <= 10) {
			// 진척률만 업데이트
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		} else if (prgrsRt >= 11 && prgrsRt < 40) {
			// 진척률만 업데이트
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd , prgrsRt);
		} else if (prgrsRt == 40) {
			int sttsCd = 3;
			// 진척률 + 진행상태
			log.info("sttsCd : " + sttsCd);
			srDemandService.updateSrDemandStts(srNo, sttsCd);
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		} else if (prgrsRt >= 41 && prgrsRt < 70) {
			// 진척률만 업데이트
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		} else if (prgrsRt == 70) {
			int sttsCd = 4;
			// 진척률 + 진행상태
			log.info("sttsCd : " + sttsCd);
			srDemandService.updateSrDemandStts(srNo, sttsCd);
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		} else if (prgrsRt >= 71 && prgrsRt < 80) {
			// 진척률만 업데이트
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		} else if (prgrsRt == 80) {
			// 진척률 + 진행상태
		} else if (prgrsRt >= 81 && prgrsRt < 90) {
			// 진척률만 업데이트
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		} else if (prgrsRt == 90) {
			// 진척률 + 진행상태
		} else if (prgrsRt >= 91 && prgrsRt < 100) {
			// 진척률만 업데이트
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		} else if (prgrsRt == 100) {
			int sttsCd = 5;
			log.info("sttsCd : " + sttsCd);
			// 진척률 + 진행상태
			srDemandService.updateSrDemandStts(srNo, sttsCd);
			progressDao.updateProgressByPrgrsId(prgrsId, bgngYmd, endYmd, prgrsRt);
		}
	}

	/**
	 * 진척데이터 삽입
	 * @author 신정은
	 */
	public void addProgress(String srNo, List<String> pNames) {
		progressDao.insertProgress(srNo, pNames);
	}
	/**
	 * 
	 * @author 여수한 작성일자 : 2023-03-08
	 * @return 자원 넣으면 요구사항(개발중)시작
	 */
	public void startProgress(String srNo) {
		progressDao.startProgress(srNo);
	}
}
