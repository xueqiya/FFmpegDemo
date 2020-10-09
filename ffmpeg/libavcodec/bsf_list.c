static const AVBitStreamFilter * const bitstream_filters[] = {
    &ff_aac_adtstoasc_bsf,
    &ff_av1_frame_merge_bsf,
    &ff_h264_mp4toannexb_bsf,
    &ff_null_bsf,
    &ff_vp9_superframe_bsf,
    &ff_vp9_superframe_split_bsf,
    NULL };
