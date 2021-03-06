package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.DailyData;
import com.nieyue.service.DailyDataService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 每日数据控制类
 * @author yy
 *
 */
@Api(tags={"dailyData"},value="每日数据",description="每日数据")
@RestController
@RequestMapping("/dailyData")
public class DailyDataController {
	@Resource
	private DailyDataService dailyDataService;
	
	/**
	 * 每日数据分页浏览
	 * @param orderName 商品排序每日数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "每日数据", notes = "每日数据")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="createDate",value="创建日期",dataType="int", paramType = "query"),
		  @ApiImplicitParam(name="goodsId",value="物品ID",dataType="int", paramType = "query"),
		  @ApiImplicitParam(name="acountId",value="账户ID",dataType="int", paramType = "query"),
		  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="update_date"),
		  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
		  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingDailyData(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="goodsId",required=false)Integer goodsId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="daily_data_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
			List<DailyData> list = new ArrayList<DailyData>();
			list= dailyDataService.browsePagingDailyData(createDate,goodsId,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
				
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 统计每日数据
	 * @param orderName 商品排序每日数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/statisticsDailyData", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList statisticsDailyData(
			@RequestParam(value="startDate",required=false)Date startDate,
			@RequestParam(value="endDate",required=false)Date endDate,
			@RequestParam(value="goodsId",required=false)Integer goodsId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="create_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
		List<DailyData> l = dailyDataService.statisticsDailyData(startDate,endDate,goodsId,acountId,pageNum, pageSize, orderName, orderWay);
		if(l.size()>0){
			return ResultUtil.getSlefSRSuccessList(l);
			
		}else{
			return ResultUtil.getSlefSRFailList(l);
		}
	}
	/**
	 * 每日数据修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateDailyData(@ModelAttribute DailyData dailyData,HttpSession session)  {
		boolean um = dailyDataService.updateDailyData(dailyData);
		return ResultUtil.getSR(um);
	}
	/**
	 * 每日数据增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addDailyData(@ModelAttribute DailyData dailyData, HttpSession session) {
		boolean am = dailyDataService.addDailyData(dailyData);
		return ResultUtil.getSR(am);
	}
	/**
	 * 每日数据删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delDailyData(
			@RequestParam("dailyDataId") Integer dailyDataId,HttpSession session)  {
		boolean dm =dailyDataService.delDailyData(dailyDataId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 每日数据浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="goodsId",required=false)Integer goodsId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		int count = dailyDataService.countAll(createDate,goodsId,acountId);
		return count;
	}
	/**
	 * 每日数据单个加载
	 * @return
	 */
	@RequestMapping(value = "/{dailyDataId}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loadDailyData(@PathVariable("dailyDataId") Integer dailyDataId,HttpSession session)  {
		List<DailyData> list = new ArrayList<DailyData>();
		DailyData DailyData = dailyDataService.loadDailyData(dailyDataId,null,null,null);
			if(DailyData!=null &&!DailyData.equals("")){
				list.add(DailyData);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 每日数据单个加载
	 * @return
	 */
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loadDailyDataByParams(
			@RequestParam(value="dailyDataId",required=false)Integer dailyDataId,
			@RequestParam(value="crateDate",required=false)Date crateDate,
			@RequestParam(value="goodsId",required=false)Integer goodsId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		List<DailyData> list = new ArrayList<DailyData>();
		DailyData dailyData = dailyDataService.loadDailyData(dailyDataId,crateDate,goodsId,acountId);
		if(dailyData!=null &&!dailyData.equals("")){
			list.add(dailyData);
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}
	
}
